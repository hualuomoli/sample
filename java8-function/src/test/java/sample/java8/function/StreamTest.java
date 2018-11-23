package sample.java8.function;

import java.util.UUID;

import org.junit.Test;

public class StreamTest {

  @Test
  public void test() {
    new Stream<String>() //
        .create(() -> UUID.randomUUID().toString().substring(0, 8))//
        .filter(random -> false);
  }

}
