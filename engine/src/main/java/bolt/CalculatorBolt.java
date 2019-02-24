package bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import service.CalculatorService.AttrValue;


public class CalculatorBolt extends BaseRichBolt {

	private OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
			this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String attrId = input.getStringByField("attrId");
		Object obj = input.getValueByField("attrValue");
		AttrValue attrValue = (AttrValue)obj;
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("attrId", "value"));
	}

}
