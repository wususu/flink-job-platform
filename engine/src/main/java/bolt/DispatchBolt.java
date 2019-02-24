package bolt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import conf.BeanUtils;
import conf.MapperHelper;
import mapper.AttrConfMapper;
import mapper.TableConfMapper;
import model.AttrConf;
import model.CUDModel;
import model.TableConf;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.daemon.acker;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
    private TableConfMapper tableConfMapper ;
    private AttrConfMapper attrConfMapper ;


    public void prepare(Map map, TopologyContext topologyContext, OutputCollector collector) {
        this.collector = collector;
//        tableConfMapper = MapperHelper.getMapper(TableConfMapper.class);
        attrConfMapper = MapperHelper.getMapper(AttrConfMapper.class);
//        List<TableConf> tableConfs = tableConfMapper.getAll();
        List<AttrConf> attrConfMappers = attrConfMapper.getAll();
    }

    public void execute(Tuple tuple) {
        String raw = tuple.getStringByField("value");
        if (StringUtils.isNotBlank(raw)){
        	CUDModel cudModel = null;
        	try{
        		cudModel = JSONObject.parseObject(raw, CUDModel.class);
        	}catch(JSONException e) {
        		LOG.error("dirty data: {}", raw);
        		return ;
        	}
        	if (cudModel != null) {
        		LOG.info("EMIT: {}", cudModel.toString());
            	this.collector.emit(new Values(cudModel));
			}
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    		outputFieldsDeclarer.declare(new Fields("cudModel")); 
    }
}
