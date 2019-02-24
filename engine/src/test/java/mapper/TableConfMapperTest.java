package mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.TableConf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class TableConfMapperTest {

	@Autowired
	TableConfMapper tableConfMapper;
	
	TableConf  tableConf;
	
	{
		tableConf = new TableConf();
		tableConf.setIsOnline(1);
		tableConf.setCreater("dw_wupeijian");
		tableConf.setCreateTime(new Date());
		tableConf.setType("binlog");
		tableConf.setName("test_maxwell.user_info");
		tableConf.setTopic("topicName");
	}
	
	@Test
	public void testInsert() {
		int ret = tableConfMapper.insert(tableConf);
		System.out.println(ret);
	}
	
	@Test
	public void testGetAll() {
		List<TableConf> tableConfs = tableConfMapper.getAll();
		System.out.println(tableConfs.size());
		System.out.println(tableConfs);
	}
	
}
