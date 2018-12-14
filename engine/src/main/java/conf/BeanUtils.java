package conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class BeanUtils {


    public static Logger LOG = LoggerFactory.getLogger(BeanUtils.class);
    public static ConfigurableApplicationContext applicationContext;


    static {
        try {
            if (applicationContext == null) {
                applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
            }
            LOG.info("{}",applicationContext);

        }catch (Exception e){
            LOG.error("初始化配置信息失败,{}", e.getMessage());
        }
    }

    public static<T> T getService(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
