package com.yuki.bigdata.mapper;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.yuki.bigdata.dto.AttrConfQuery;
import common.PageList;
import model.AttrConf;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

@Repository
public interface AttrConMapper {


    String TABLE = "attr_conf";
    String COLUMNS = "aid,table_id, attr_type, cal_type, db_name, tbl_name, dimension_type, dimension_key, field, field_type, cal_expression, create_time, is_online";

    @Select("select * from attr_conf where aid=#{aid}")
    @ResultMap("mapper.AttrConfMapper.BaseResultMap")
    AttrConf get(Integer aid);

    @InsertProvider(type = AttrConfMapperProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "aid")
    int insert(AttrConf attrConf);

    @SelectProvider(type = AttrConfMapperProvider.class, method = "page")
    @ResultMap("mapper.AttrConfMapper.BaseResultMap")
    PageList<AttrConf> page(AttrConfQuery attrConfQuery);

    @InsertProvider(type=AttrConfMapperProvider.class, method="insertMap")
    void insertComplexRealMap(@Param("complexAttrId")String complexAttrId, @Param("realAttrIds") List<String> realAttrIds);

    
    class AttrConfMapperProvider {

        public String insert(AttrConf attrConf) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    INTO_COLUMNS(COLUMNS);
                    INTO_VALUES("#{aid},#{tableId},#{attrType},#{calType},#{dbName},#{tblName},#{dimensionType},#{dimensionKey},#{field},#{fieldType}, #{calExpression},#{createTime},#{isOnline}");
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
        
    	public String insertMap(@Param("complexAttrId")String complexAttrId, @Param("realAttrIds") List<String> realAttrIds) {
    		StringBuilder sb = new StringBuilder("insert into real_complex_attr_map(complex_attrid, real_attrid) values ");
    		List<String> list = Lists.newArrayList();
    		for (String realAttrId : realAttrIds) {
				list.add("( #{complexAttrId}, '" +realAttrId + "')");
			}
    		sb.append(Joiner.on(",").join(list));
    		return sb.toString();
    	}
    }
}
