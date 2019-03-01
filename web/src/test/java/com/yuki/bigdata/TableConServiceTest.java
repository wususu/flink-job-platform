package com.yuki.bigdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuki.bigdata.service.TableConfService;

import model.TableConf;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SailfishApplication.class)
public class TableConServiceTest {

	@Autowired
	TableConfService tableConfService;
	
	@Test
	public void testt() {
		System.out.println("111");
	}
	
	@Test
	public void testInsert() {
		TableConf tableConf = new TableConf();
		tableConf.setName("test_maxwell.user_info");
		tableConf.setTopic("test_maxwell.user_info");
		tableConf.setCreater("dw_wupeijian");
		tableConf.setType("BINLOG");
		tableConfService.create(tableConf);
	}
	
	public static void main(String[] args) {

	}
	
}
