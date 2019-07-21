package sample.flink.socket;

import org.junit.Test;

public class WordCountTest {

    // nc -l 9000
    @Test
    public void testRun() throws Exception {
        WordCount.run("localhost", 9000, "\n");
    }

}
