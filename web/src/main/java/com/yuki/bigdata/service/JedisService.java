package com.yuki.bigdata.service;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author janke
 * @date 2018/12/11.
 */
@Component
public class JedisService {

	private static final Logger LOG = LoggerFactory.getLogger(JedisPool.class);

    private JedisPool jedisPool;
    private Jedis jedis;
    
    @Value("${redis.ip}")
    private String ip;
    
    @Value("${redis.port}")
    private Integer port;
    
    @Value("${redis.pool.maxTotal}")
    private Integer maxTotal;
    
    @Value("${redis.pool.maxIdle}")
    private Integer maxldle;
    
    @Value("${redis.pool.maxWait}")
    private Integer maxWait;
    
    @Value("${redis.pool.testOnBorrow}")
    private Boolean testOnBorrow;
    
    @Value("${redis.pool.testOnReturn}")
    private Boolean testOnReturn;
    
    @PostConstruct
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        LOG.info("ip:" + ip + "\n port:" + port +" \n");
        config.setMaxIdle(maxldle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        jedisPool = new JedisPool(config, ip, port, 100000);
    }

    public synchronized void hset(String attrId, String keyId, String value) {
        Jedis jedis = jedisPool.getResource();
        try{
        jedis.hset(attrId, keyId, value);
        }finally {
			closeJedis(jedis);
		}
    }

    public String hget(String attrId, String keyId) {
//    	 jedis = jedisPool.getResource();
        Jedis jedis = jedis = jedisPool.getResource();
        String value = null;
        try{
    	 value =  jedis.hget(attrId, keyId);
        }finally{
        	closeJedis(jedis);
        }
        return value;
    }
    public static void closeJedis(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            LOG.error("释放资源异常：" + e);
        }
    }

}
