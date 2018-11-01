package sample.springboot.storm.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

import sample.springboot.storm.bolt.SplitSentenceBolt;
import sample.springboot.storm.bolt.WordCountBolt;
import sample.springboot.storm.bolt.WordReportBolt;
import sample.springboot.storm.spout.RandomSentenceSpout;

/**
 * 构建
 */
public class WordCountTopology {

  /**
   * 运行
   * @throws Exception 运行异常
   */
  public void run() throws Exception {
    // 组建拓扑，并使用流分组
    TopologyBuilder builder = new TopologyBuilder();
    // 执行spout的名称以及处理器
    builder.setSpout("spout", new RandomSentenceSpout());
    // 指定bolt的名称以及处理器(随机分组,多个机器同时处理)
    builder.setBolt("split-bolt", new SplitSentenceBolt(), 5).shuffleGrouping("spout"); // 对哪个组件输出进行处理
    // 指定bolt的名称及处理器(按照属性分组,多个机器处理结果进行归整)
    builder.setBolt("count-bolt", new WordCountBolt(), 5).fieldsGrouping("split-bolt", new Fields("word")); // 对哪个组件输出的哪个属性分组
    // 指定bolt的名称及处理器(全局归整)
    builder.setBolt("report-bolt", new WordReportBolt(), 10).globalGrouping("count-bolt"); // 对哪个组件输出进行处理

    //配置
    Config config = new Config();
    config.setDebug(false);

    // 开启处理
    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("word-topology", config, builder.createTopology());

    // 20s后关闭统计(机器快的可调小)
    Utils.sleep(1000 * 20);
    cluster.killTopology("word-topology");
    cluster.shutdown();
  }

}
