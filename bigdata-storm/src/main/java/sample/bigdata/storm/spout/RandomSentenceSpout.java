package sample.bigdata.storm.spout;

import java.util.Map;
import java.util.Random;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

/**
 * 参数数据
 */
public class RandomSentenceSpout extends BaseRichSpout {

  private static final long serialVersionUID = 1075199953028832440L;

  private final String[] sentences = new String[] { //
      "the cow jumped over the moon"//
      , "an apple a day keeps the doctor away"//
      , "four score and seven years ago"//
      , "snow white and the seven dwarfs"//
      , "i am at two with nature" };

  private SpoutOutputCollector collector;
  private Random random;

  @SuppressWarnings("rawtypes")
  @Override
  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector = collector;
    this.random = new Random();
  }

  @Override
  public void nextTuple() {
    Utils.sleep(200); // 调整数据流的频率

    // 获取数据(这里使用随机获取数组中的值作为输入)
    String sentence = sentences[random.nextInt(sentences.length)];
    // 输出数据
    collector.emit(new Values(sentence)); // 属性值
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("sentence")); // 属性名
  }

}
