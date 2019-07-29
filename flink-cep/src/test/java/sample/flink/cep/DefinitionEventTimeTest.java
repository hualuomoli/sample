package sample.flink.cep;

import org.junit.Ignore;
import org.junit.Test;

public class DefinitionEventTimeTest {

    private static final DefinitionEventTime service = new DefinitionEventTime();

    @Test
    @Ignore
    public void testPeriodic() throws Exception {
        service.periodic();
    }

    @Test
    @Ignore
    public void testPunctuated() throws Exception {
        service.punctuated();
    }

}
