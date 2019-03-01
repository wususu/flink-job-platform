package com.yuki.bigdata.conf;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import common.Page;
import common.PageList;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


@Intercepts({
        @Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MyBatisPageInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisPageInterceptor.class);

    private String databaseType = "mysql";

    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        Object paramerFind = parameter;
        if (parameter instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap)parameter;
            List paramList = Lists.newArrayList(paramMap.values());
            for (Object param: paramList) {
                if (param instanceof Page) {
                    paramerFind = param;
                    break;
                }
            }
        }
        if (parameter instanceof Page || paramerFind instanceof Page) {
            Page page = (Page) paramerFind;
            //获取动态sql，BoundSql
            final BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().trim().replaceAll(";$", "");
            String pageSql = this.getPageSql(page, sql);
            long total = queryTotal(sql, ms, boundSql, parameter);
            page.setTotal(total);
            //获取新的RowBounds
            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
            //获取新的MappedStatement
            queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, pageSql);
            Object ret = invocation.proceed();
            PageList<?> pageList = new PageList<>(page, (List) ret);
            return pageList;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.databaseType = properties.getProperty("databaseType");
    }

    private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * @param page 分页对象
     * @param sql  原sql语句
     * @return
     */
    private String getPageSql(Page page, String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if ("mysql" .equalsIgnoreCase(databaseType)) {
            return getPageSql(page, sqlBuffer);
        }
        return sqlBuffer.toString();
    }

    /**
     * 获取Mysql数据库的分页查询语句
     *
     * @param page      分页对象
     * @param sqlBuffer 包含原sql语句的StringBuffer对象
     * @return Mysql数据库分页语句
     */
    private String getPageSql(Page page, StringBuffer sqlBuffer) {
        if (page.getOrderColumn() != null && !page.getOrderColumn().isEmpty()) {
            sqlBuffer.append("order by ");
            sqlBuffer.append(Joiner.on(",").join(page.getOrderColumn()));
            sqlBuffer.append(" " + page.getOrderType());
        }
        //计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        int offset = page.getOffset();
        sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        return sqlBuffer.toString();
    }

    private long queryTotal(String sql, MappedStatement mappedStatement, BoundSql boundSql, Object parameter) throws SQLException {
        String countSql = getCountSql(sql);

        Connection connection;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        long count = 0;
        try {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            countStmt = connection.prepareStatement(countSql);
            DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement, parameter, boundSql);
            handler.setParameters(countStmt);
            rs = countStmt.executeQuery();
            if (rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("==> Preparing: {}", countSql);
            logger.debug("<== Total: {}", count);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } finally {
                if (countStmt != null) {
                    countStmt.close();
                }
            }
        }
        return count;
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        int indexLo = sql.toLowerCase().indexOf("from");
        int index = 0;
        if (indexLo != -1) {
            index = indexLo;
        }
        return "select count(1) " + sql.substring(index);
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;


        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }


        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
