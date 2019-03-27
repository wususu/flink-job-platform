package mapper;

import model.AttrConf;
import model.FieldType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class AttrConfMapperTest {

    @Autowired
    AttrConfMapper attrConfMapper;
//    INSERT INTO         attr_conf         (aid, table_id, attr_type, cal_type, db_name, tbl_name, dimension_type, dimension_key, field, field_type,cal_expression, create_time, is_online)  
//    VALUES         ('fr20191', 3, 1, 1, 'test_maxwell', 'user_info', 'Integer', 'userid', 'name',? ?, ?, ?)
//    @Test
    public void testInsert() throws Exception{
        AttrConf attrConf = new AttrConf();
        attrConf.setAid("fr20193");
        attrConf.setAttrType(1);
        attrConf.setCalExpression(null);
        attrConf.setCalType(6);
        attrConf.setCreateTime(new Date());
        attrConf.setDbName("test_maxwell");
        attrConf.setTblName("user_info");
        attrConf.setDimensionKey("userid");
        attrConf.setDimensionType("u");
        attrConf.setField("name + userid +','");
        attrConf.setFieldType(FieldType.STRING.val);
        attrConf.setIsOnline(1);
        attrConf.setTableId(3);
        attrConfMapper.insert(attrConf);
    }
    
    @Test
    public void testGet() {
    	List<String> realIds = attrConfMapper.getRealAttrByComplexAttrId("fr20192");
    	List<String> complexIds = attrConfMapper.getComplexAttrByRealAttrId("fr20191");
    	System.out.println(realIds);
    	System.out.println(complexIds);
    }
    
//    @Test
    public void testInsertMap() {
    	try{
    	String complex = "fr20192";
    	List<String> reals = Lists.newArrayList();
    	reals.add("fr20191");
    	attrConfMapper.insertComplexRealMap(complex, reals);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    }

}
