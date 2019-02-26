package mapper;

import model.AttrConf;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

@Repository
public interface AttrConfMapper {
    List<AttrConf> getAll();

    AttrConf getOne(Integer aid);

    void insert(AttrConf user);

    void update(AttrConf user);

    void delete(Integer id);
    
    @SelectProvider(type = AttrConfMapperProvider.class, method="getRealAttr")
    List<String> getRealAttrByComplexAttrId(String complexAttrId);
    
    @SelectProvider(type = AttrConfMapperProvider.class, method="getComplexAttr")
    List<String> getComplexAttrByRealAttrId(String realAttrId);
    
    @InsertProvider(type=AttrConfMapperProvider.class, method="insertMap")
    void insertComplexRealMap(@Param("complexAttrId")String complexAttrId, @Param("realAttrIds") List<String> realAttrIds);

    @SelectProvider(type = AttrConfMapperProvider.class, method="selectComplexAttrConfs")
    @ResultMap("mapper.AttrConfMapper.BaseResultMap")
    List<AttrConf> selectComplexAttrConfs(String realAttrId);
    
    class AttrConfMapperProvider {
    	
    	public String selectComplexAttrConfs(String realAttrId) {
    		String sql = "select a.aid,  a.table_id,  a.attr_type,  a.cal_type, a.db_name, a.tbl_name, a.dimension_type, "
    				+ " a.dimension_key,a.field, a.field_type, a.cal_expression, a.create_time, a.is_online"
    				+ " from attr_conf a left join (select complex_attrid as cid from real_complex_attr_map where real_attrid = #{realAttrId}) b"
    				+ " on a.aid = b.cid";
    		return sql;
    	}
    	
    	public String getComplexAttr(String realAttrId) {
    		String sql = "select complex_attrid from real_complex_attr_map where real_attrid = #{realAttrId}";
    		return sql;
    	}
    	
    	public String getRealAttr(String complexAttrId) {
    		String sql = "select real_attrid from real_complex_attr_map where  complex_attrid= #{complexAttrId}";
    		return sql;
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
