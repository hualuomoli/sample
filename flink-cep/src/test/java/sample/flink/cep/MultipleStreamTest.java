package sample.flink.cep;

import org.junit.Test;

public class MultipleStreamTest {

    private static final MultipleStream stream = new MultipleStream();

    @Test
    public void testRun() throws Exception {
        stream.run();
    }

}
