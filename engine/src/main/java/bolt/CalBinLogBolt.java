package bolt;

import model.CUDModel;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class CalBinLogBolt extends BaseRichBolt {

    private OutputCollector collector;

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        this.collector = collector;
    }

    public void execute(Tuple tuple) {
        Object value = tuple.getValueByField("cudModel");
        if (value != null) {
            CUDModel model = (CUDModel)value;

        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
