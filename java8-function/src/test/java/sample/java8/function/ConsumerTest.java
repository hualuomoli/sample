package sample.java8.function;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// T -> void
public class ConsumerTest {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerTest.class);

  @Test
  public void test() {

    List<String> ids = Lists.newArrayList("123", "jack ma", "hello comsumer");

    new Stream(ids).filter(id -> id.length() > 3).accept(logger::info);

  }

  private class Stream {

    private List<String> ids;

    public Stream(List<String> ids) {
      this.ids = Lists.newArrayList(ids);
    }

    Stream filter(Predicate<String> predicate) {
      for (Iterator<String> it = ids.iterator(); it.hasNext();) {
        if (!predicate.test(it.next())) {
          it.remove();
        }
      }
      return this;
    }

    void accept(Consumer<String> consumer) {
      for (String id : ids) {
        consumer.accept(id);
      }
    }

  }

}
