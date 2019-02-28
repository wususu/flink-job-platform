package service;

import conf.Configurator;
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

    public static final String REDIS_POOL_MAXIDLE = "redis.pool.maxIdle";
    public static final String REDIS_POOL_MAXWAIT = "redis.pool.maxWait";
    public static final String REDIS_POOL_TEST_ON_BORROW = "redis.pool.testOnBorrow";
    public static final String REDIS_POOL_TEST_ON_RETURN = "redis.pool.testOnReturn";
    public static final String REDIS_IP = "redis.ip";
    public static final String REDIS_PORT = "redis.port";
    public static final String REDIS_PASS = "redis.auth";

    private static JedisPool jedisPool;
    private static Jedis jedis;
    private static String pass = Configurator.get(REDIS_PASS);

    static{
        JedisPoolConfig config = new JedisPoolConfig();
        String host = Configurator.get(REDIS_IP);
        int port = Configurator.getInt(REDIS_PORT);
        config.setMaxIdle(Configurator.getInt(REDIS_POOL_MAXIDLE));
        config.setMaxWaitMillis(Configurator.getInt(REDIS_POOL_MAXWAIT));
        config.setTestOnBorrow(Configurator.getBoolean(REDIS_POOL_TEST_ON_BORROW));
        config.setTestOnReturn(Configurator.getBoolean(REDIS_POOL_TEST_ON_RETURN));
        jedisPool = new JedisPool(config, host, port);
        jedis = jedisPool.getResource();
    }

    public void hset(String attrId, String keyId, String value) {
        jedis.hset(attrId, keyId, value);
    }

    public String hget(String attrId, String keyId) {
//    	 jedis = jedisPool.getResource();
    	 return jedis.hget(attrId, keyId);
    }


}
