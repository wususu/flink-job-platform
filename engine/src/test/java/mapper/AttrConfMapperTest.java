package mapper;

import model.AttrConf;
import model.FieldType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class AttrConfMapperTest {

    @Autowired
    AttrConfMapper attrConfMapper;
//    INSERT INTO         attr_conf         (aid, table_id, attr_type, cal_type, db_name, tbl_name, dimension_type, dimension_key, field, field_type,cal_expression, create_time, is_online)  
//    VALUES         ('fr20191', 3, 1, 1, 'test_maxwell', 'user_info', 'Integer', 'userid', 'name',? ?, ?, ?)
    @Test
    public void testInsert() throws Exception{
        AttrConf attrConf = new AttrConf();
        attrConf.setAid("fr20191");
        attrConf.setAttrType(1);
        attrConf.setCalExpression(null);
        attrConf.setCalType(1);
        attrConf.setCreateTime(new Date());
        attrConf.setDbName("test_maxwell");
        attrConf.setTblName("user_info");
        attrConf.setDimensionKey("userid");
        attrConf.setDimensionType("Integer");
        attrConf.setField("name");
        attrConf.setFieldType(FieldType.STRING.val);
        attrConf.setIsOnline(1);
        attrConf.setTableId(3);
        attrConfMapper.insert(attrConf);
    }

}
