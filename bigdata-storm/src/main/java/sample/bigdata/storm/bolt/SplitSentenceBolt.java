package sample.bigdata.storm.bolt;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 分割文章为单词
 */
public class SplitSentenceBolt extends BaseBasicBolt {

  private static final long serialVersionUID = -7813521828738439815L;
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    String sentence = null;
    // 获取数据方式
    // 1、使用位置获取
    sentence = input.getString(0);
    // 2、使用属性名获取
    sentence = input.getStringByField("sentence");
    if (StringUtils.isBlank(sentence)) {
      logger.warn("sentence is empty.");
      return;
    }

    // 输出数据
    Lists.newArrayList(StringUtils.split(sentence, " ")).stream().forEach((word) -> {
      collector.emit(new Values(word));
    });
    // end method
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("word"));
  }

}