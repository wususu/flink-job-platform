package helper;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.AttrConf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class AttrConfCacheHelperTest {

	private AttrConfCacheHelper attrConfCacheHelper = AttrConfCacheHelper.instance();
	
	@Test
	public void test() {
//		Map<String, AttrConf> cache = attrConfCacheHelper.getAll();
//		System.out.println(cache);
	}
	
}
