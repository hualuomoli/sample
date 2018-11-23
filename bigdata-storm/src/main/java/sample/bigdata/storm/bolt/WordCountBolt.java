package sample.bigdata.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统计
 */
public class WordCountBolt extends BaseBasicBolt {

  private static final long serialVersionUID = 7156615757966959258L;
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private static final Map<String, Long> counts = new HashMap<String, Long>();

  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    String word = input.getStringByField("word");
    if (StringUtils.isBlank(word)) {
      logger.warn("word is empty.");
      return;
    }
    Long count = counts.get(word);
    if (count == null) {
      count = 0L;
    }
    count++;
    counts.put(word, count);
    collector.emit(new Values(word, count));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("word", "count"));
  }

}
