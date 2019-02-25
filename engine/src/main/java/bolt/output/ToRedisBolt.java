package bolt.output;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import conf.BeanUtils;
import service.CalculatorService.AttrValue;
import service.JedisService;

import java.util.Map;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class ToRedisBolt extends BaseRichBolt {

	private OutputCollector collector;
	private JedisService jedisService;
	
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    	this.collector = outputCollector;
    	this.jedisService = BeanUtils.getService(JedisService.class);
    }

    public void execute(Tuple tuple) {
    	String attrId = tuple.getStringByField("attrId");
    	String newVal = tuple.getStringByField("value");
    	Object object = tuple.getValueByField("attrValue");
    	AttrValue attrValue = (AttrValue)object;
    	String key = attrValue.getKey();
    	jedisService.hset(attrId, key, newVal);
    	collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
