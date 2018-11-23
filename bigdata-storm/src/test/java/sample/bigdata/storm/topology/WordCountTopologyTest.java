package sample.bigdata.storm.topology;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class WordCountTopologyTest {

  private WordCountTopology wordCountTopology;

  @Before
  public void before() {
    wordCountTopology = new WordCountTopology();
  }

  @Test
  public void testRun() throws Exception {
    wordCountTopology.run();
  }

}
