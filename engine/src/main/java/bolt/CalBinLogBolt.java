package bolt;

import model.AttrConf;
import model.CUDModel;
import model.TableConf;
import model.XTable;
import service.CalculatorService;
import service.CalculatorService.AttrValue;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import conf.BeanUtils;
import helper.AttrConfCacheHelper;
import helper.TableConfCacheHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author janke
 * @date 2018/12/11.
 */
public class CalBinLogBolt extends BaseRichBolt {

	private static final Logger LOG = LoggerFactory.getLogger(CalBinLogBolt.class);
	
    private OutputCollector collector;
    private CalculatorService calculatorService;
    private Map<String, TableConf> tblConfCache;
    private Map<XTable, List<AttrConf>> attrConfCache;
    
    
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
    	this.tblConfCache = TableConfCacheHelper.instance().getAll();
    	this.attrConfCache = AttrConfCacheHelper.instance().getAll();
        this.collector = collector;
        this.calculatorService = BeanUtils.getService(CalculatorService.class);
    }

    public void execute(Tuple tuple) {
        LOG.info(Arrays.toString(tuple.getValues().toArray()));
        Object value = tuple.getValueByField("cudModel");
        if (value != null) {
            CUDModel model = (CUDModel)value;
            String fullName = model.getDatabase() + "." + model.getTable();
            TableConf tableConf = this.tblConfCache.get(fullName);
       
            if (tableConf == null || tableConf.getIsOnline() == 0) {
				return ;
			}
            List<AttrConf> attrConfs = this.attrConfCache.get(new XTable(model.getDatabase(), model.getTable()));
            if (attrConfs != null) {
            	List<AttrValue> attrValues = calculatorService.calculate(model, attrConfs);
                for (AttrValue attrValue : attrValues) {
    				collector.emit(new Values(attrValue.getAid(), attrValue));
    			}
			}
        }
    	collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    	outputFieldsDeclarer.declare(new Fields("attrId", "attrValue"));
    }
}
