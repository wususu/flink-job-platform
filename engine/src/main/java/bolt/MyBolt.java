package bolt;

import clojure.lang.Obj;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author janke
 * @date 2018/12/10.
 */
public class MyBolt extends BaseRichBolt {

    private static final long serialVersionUID = -1447670521890169514L;
    private final static Logger LOGGER = LoggerFactory.getLogger(MyBolt.class);
    private OutputCollector collector;


    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        this.collector = collector;
    }

    public void execute(Tuple tuple) {
        System.out.println(tuple);
        List<Object> values = tuple.getValues();
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
