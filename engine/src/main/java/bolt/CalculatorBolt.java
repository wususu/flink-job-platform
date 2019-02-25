package bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clojure.java.jmx.Bean;
import conf.BeanUtils;
import service.Calculater;
import service.CalculatorService.AttrValue;


public class CalculatorBolt extends BaseRichBolt {

	private static final Logger LOG = LoggerFactory.getLogger(CalculatorBolt.class);
	
	private OutputCollector collector;
	private Calculater calculater;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
			this.collector = collector;
			this.calculater = BeanUtils.getService(Calculater.class);
	}

	@Override
	public void execute(Tuple tuple) {
		String attrId = tuple.getStringByField("attrId");
		Object obj = tuple.getValueByField("attrValue");
		AttrValue attrValue = (AttrValue)obj;
		try{
		String newVal = calculater.cal(attrValue);
		collector.emit(new Values(attrId, newVal, attrValue));
    	collector.ack(tuple);
		}catch (Exception e) {
			LOG.error("cal fail: " + e.getMessage(), e);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("attrId", "value", "attrValue"));
	}

}
