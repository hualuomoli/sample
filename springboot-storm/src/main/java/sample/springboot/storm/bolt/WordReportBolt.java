package sample.springboot.storm.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 报告
 */
public class WordReportBolt extends BaseBasicBolt {

  private static final long serialVersionUID = 1L;
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void execute(Tuple input, BasicOutputCollector collector) {
    String word = input.getStringByField("word");
    Long count = input.getLongByField("count");
    logger.info("report word={}, count={}", word, count);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
  }

}
