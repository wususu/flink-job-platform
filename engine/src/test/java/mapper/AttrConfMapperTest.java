package mapper;

import model.AttrConf;
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

    @Test
    public void testInsert(){
        AttrConf attrConf = new AttrConf();
        attrConf.setAid("aid");
        attrConf.setAttrType(1);
        attrConf.setCalExpression("expression");
        attrConf.setCalType(1);
        attrConf.setCreateTime(new Date());
        attrConf.setDbName("dbname");
        attrConf.setTblName("tblName");
        attrConf.setDimensionKey("key");
        attrConf.setDimensionType("keyType");
        attrConf.setField("field");
        attrConf.setIsOnline(0);
        attrConf.setTableId(1);
        attrConfMapper.insert(attrConf);
    }

}
