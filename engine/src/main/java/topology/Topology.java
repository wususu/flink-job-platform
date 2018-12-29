package topology;

import bolt.CalBinLogBolt;
import bolt.DispatchBolt;
import mapper.UserMapper;
import model.UserEntity;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


/**
 * @author janke
 * @date 2018/12/10.
 */
public class Topology {

    public static String BINLOG_SPOUT = "binlog_spout";
    public static String DISPATCH_BOLT = "dispatch_bolt";
    public static String CAL_BINLOG_BOLT = "cal_binlog_bolt";

    public static boolean isLocal(){
        return true;
    }

    public static void start() {
        TopologyBuilder tp = new TopologyBuilder();
        DispatchBolt dispatchBolt = new DispatchBolt();
        CalBinLogBolt calBinLogBolt = new CalBinLogBolt();
        tp.setSpout(BINLOG_SPOUT, new KafkaSpout<String, String>(KafkaSpoutConfig.<String, String>builder("172.28.21.32:9092", "maxwell").build()));
        tp.setBolt(DISPATCH_BOLT, dispatchBolt).shuffleGrouping(BINLOG_SPOUT);
        tp.setBolt(CAL_BINLOG_BOLT, calBinLogBolt).shuffleGrouping(DISPATCH_BOLT);

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
        UserMapper userMapper = actx.getBean(UserMapper.class);
        List<UserEntity> userEntities = userMapper.getAll();
        System.out.println(userEntities);
        start();
    }
}
