package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author janke
 * @date 2018/12/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class JedisServiceTest {

    @Autowired
    JedisService jedisService;


    @Test
    public void test(){
        System.out.println(jedisService);
        System.out.println(jedisService.hget("attr_id","key_id"));
    }

}
