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
    	try{
        System.out.println(jedisService);
//        Thread.sleep(25000);
        Runnable runnable = new Runnable() {
        	JedisService js =jedisService;
			public void run() {
		        for(int i=0; i< 100;i++){
		            System.out.println(js.hget("key","value"));
		            }
			}
		};
		
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		thread1.start();
		thread2.start();
		thread3.start();
    	
    	Thread.sleep(100000);
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
    	}
    	}

}
