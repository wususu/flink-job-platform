package topology;

import bolt.MyBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;


/**
 * @author janke
 * @date 2018/12/10.
 */
public class Topology {

    public static boolean isLocal(){
        return true;
    }

    public static void start() {
        final TopologyBuilder tp = new TopologyBuilder();
        tp.setSpout("kafka_spout", new KafkaSpout<String, String>(KafkaSpoutConfig.<String, String>builder("172.28.21.32:9092", "maxwell").build()));
        tp.setBolt("src/main/java/bolt", new MyBolt()).shuffleGrouping("kafka_spout");
        StormTopology topology = tp.createTopology();

        boolean runLocal = isLocal();
        Config conf = new Config();
        if(runLocal){
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("src/main/java/topology", conf, topology);
        }
    }
    public static void main(String[] args) {
        start();
    }
}
