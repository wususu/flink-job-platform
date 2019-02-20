package bolt;

import model.AttrConf;
import model.CUDModel;
import model.TableConf;
import model.XTable;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import helper.AttrConfCacheHelper;
import helper.TableConfCacheHelper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class CalBinLogBolt extends BaseRichBolt {

    private OutputCollector collector;
    private Map<String, TableConf> tblConfCache;
    private Map<XTable, List<AttrConf>> attrConfCache;
    
    
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
    	this.tblConfCache = TableConfCacheHelper.instance().getAll();
    	this.attrConfCache = AttrConfCacheHelper.instance().getAll();
        this.collector = collector;
    }

    public void execute(Tuple tuple) {
        Object value = tuple.getValueByField("cudModel");
        if (value != null) {
            CUDModel model = (CUDModel)value;
            String fullName = model.getDatabase() + "." + model.getTable();
            TableConf tableConf = this.tblConfCache.get(fullName);
            if (tableConf == null || tableConf.getIsOnline() == 0) {
				return ;
			}
            

        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    		outputFieldsDeclarer.declare(fields);
    }
}
