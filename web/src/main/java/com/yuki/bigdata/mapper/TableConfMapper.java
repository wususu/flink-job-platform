package com.yuki.bigdata.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import com.yuki.bigdata.dto.TableConfQuery;

import common.PageList;
import model.TableConf;

@Repository
public interface TableConfMapper {

	
	@InsertProvider(type = TableConfMapperProvider.class, method = "insert")
	@Options(useGeneratedKeys=true, keyColumn="id")
	public int insert(TableConf tableConf);
	
	@SelectProvider(type=TableConfMapperProvider.class, method = "queryByPage")
	@ResultMap("mapper.TableConfMapper.BaseResultMap")
	public PageList<TableConf> queryByPage(TableConfQuery tableConfQuery);
	
	public static String TABLE = "table_conf";
	
	public static String COLUMNS = "id, type, name, topic, create_time,offline_time,creater,is_online"; 

	
	public static class TableConfMapperProvider {
		
		public String insert(TableConf tableConf) {
			SQL sql = new SQL() {
				{
					INSERT_INTO(TABLE);
					INTO_COLUMNS(COLUMNS);
    				INTO_VALUES("#{id},#{type},#{name},#{topic},#{createTime},#{offlineTime},#{creater},#{isOnline}");
				}
			};
			return sql.toString();
		}
		
		
		public String queryByPage(TableConfQuery tableConfQuery) {
			SQL sql = new SQL() {
				{
					SELECT(COLUMNS);
					FROM(TABLE);
				}
			};
			String sqlStr = sql.toString();
			return sqlStr;
		}
		
	}
	
}
