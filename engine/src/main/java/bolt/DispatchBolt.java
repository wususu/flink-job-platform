package bolt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import conf.BeanUtils;
import model.CUDModel;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.JedisService;

import java.util.List;
import java.util.Map;

/**
 * @author janke
 * @date 2018/12/10.
 */
public class DispatchBolt extends BaseRichBolt {

    private static final long serialVersionUID = -1447670521890169514L;
    private final static Logger LOG = LoggerFactory.getLogger(DispatchBolt.class);
    private OutputCollector collector;

    @Autowired
    private JedisService jedisService;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        this.collector = collector;
        JedisService jedisService = BeanUtils.getService(JedisService.class);
    }

    public void execute(Tuple tuple) {
        System.out.println(jedisService);
        System.out.println(tuple.getValues());
        String binLogString = tuple.getStringByField("value");
        if (StringUtils.isNotBlank(binLogString)){
            CUDModel cudModel = JSONObject.parseObject(binLogString, CUDModel.class);

        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
