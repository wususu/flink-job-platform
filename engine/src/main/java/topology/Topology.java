package topology;

import bolt.CalBinLogBolt;
import bolt.CalculatorBolt;
import bolt.DispatchBolt;
import bolt.output.ToRedisBolt;
import kafka.server.KafkaConfig;
import mapper.TableConfMapper;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.List;


/**
 * @author janke
 * @date 2018/12/10.
 */
public class Topology {

    public static boolean isLocal(){
        return true;
    }

    public static String KAFKA_BINLOG_SPOUT = "KAFKA_BINLOG_SPOUT";
    public static String DISPATCH_BOLT = "DISPATCH_BOLT";
    public static String BINLOG_CALCULATE_BOLT = "BINLOG_CALCULATE_BOLT";
    public static String CALCULATE_BOLT = "CALCULATE_BOLT";
    public static String TO_REDIS_BOLT = "TO_REDIS_BOLT";
    
    public static void start() {
        final TopologyBuilder tp = new TopologyBuilder();
        KafkaSpoutConfig.Builder<String, String> kafkaSpoutConfigBuilder = new KafkaSpoutConfig.Builder("95.163.197.216:9092",StringDeserializer.class,
                StringDeserializer.class,"maxwell").setFirstPollOffsetStrategy(FirstPollOffsetStrategy.LATEST);
        KafkaSpoutConfig<String, String> kafkaConfig = kafkaSpoutConfigBuilder.build();
        
        
        
        
        tp.setSpout(KAFKA_BINLOG_SPOUT, new KafkaSpout<String, String>(kafkaConfig));
        tp.setBolt(DISPATCH_BOLT, new DispatchBolt()).shuffleGrouping(KAFKA_BINLOG_SPOUT);
        tp.setBolt(BINLOG_CALCULATE_BOLT, new CalBinLogBolt()).shuffleGrouping(DISPATCH_BOLT);
        tp.setBolt(CALCULATE_BOLT, new CalculatorBolt()).fieldsGrouping(BINLOG_CALCULATE_BOLT, new Fields("attrId"));
        tp.setBolt(TO_REDIS_BOLT, new ToRedisBolt()).fieldsGrouping(CALCULATE_BOLT, new Fields("attrId"));
        StormTopology topology = tp.createTopology();

        boolean runLocal = isLocal();
        Config conf = new Config();
        if(runLocal){
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("tp", conf, topology);
        }
    }
    public static void main(String[] args) {
        args = new String[] {
                "classpath:spring/applicationContext-*.xml",
        };
        ApplicationContext actx = new ClassPathXmlApplicationContext(args);
        JdbcTemplate jdbcTemplate = actx.getBean(JdbcTemplate.class);
        TableConfMapper tableConfMapper = actx.getBean(TableConfMapper.class);
        start();
    }
}
