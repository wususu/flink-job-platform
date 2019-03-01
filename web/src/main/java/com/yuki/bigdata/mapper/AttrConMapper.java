package com.yuki.bigdata.mapper;

import com.yuki.bigdata.dto.AttrConfQuery;
import common.PageList;
import model.AttrConf;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.checkerframework.checker.fenum.qual.FenumTop;
import org.springframework.stereotype.Repository;

@Repository
public interface AttrConMapper {


    @Select("select * from attr_conf where aid=#{aid}")
    @ResultMap("mapper.AttrConfMapper.BaseResultMap")
    public AttrConf get(Integer aid);

    @InsertProvider(type = AttrConfMapperProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "aid")
    public int insert(AttrConf attrConf);

    @SelectProvider(type = AttrConfMapperProvider.class, method = "page")
    @ResultMap("mapper.AttrConfMapper.BaseResultMap")
    public PageList<AttrConf> page(AttrConfQuery attrConfQuery);

    public static String TABLE = "attr_conf";
    public static String COLUMNS = "aid,table_id attr_type, cal_type, db_name, tbl_name, dimension_type, dimension_key, field, field_type cal_expression, create_time, is_online";


    class AttrConfMapperProvider {

        public String insert(AttrConf attrConf) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    INTO_COLUMNS(COLUMNS);
                    INTO_VALUES("#{aid}#{tableId},#{attrType},#{calType},#{dbName},#{tblName},#{dimensionType},#{dimensionKey},#{field}#{fieldType} #{calExpression},#{createTime},#{isOnline}");
                }
            }.toString();
        }

        public String page(AttrConfQuery attrConfQuery) {
            return new SQL() {
                {
                    SELECT(COLUMNS);
                    FROM(TABLE);
                }
            }.toString();
        }
    }
}
