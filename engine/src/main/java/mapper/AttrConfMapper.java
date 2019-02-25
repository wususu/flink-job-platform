package mapper;

import model.AttrConf;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

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
    
    class AttrConfMapperProvider {
    	
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
    		for (String realAttrId : realAttrIds) {
				sb.append("( #{complexAttrId}, '").append(realAttrId).append("')");
			}
    		return sb.toString();
    	}
    	
    }

}
