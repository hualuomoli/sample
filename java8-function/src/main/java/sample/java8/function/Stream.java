package sample.java8.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

public class Stream<T> {

  private List<T> datas;

  public Stream() {
    this.datas = Lists.newArrayList();
  }

  public Stream(Collection<T> datas) {
    this.datas = Lists.newArrayList();
    for (T data : datas) {
      this.datas.add(data);
    }
  }

  // 生成 void -> T
  public Stream<T> create(Supplier<T> supplier) {
    datas.add(supplier.get());
    return this;
  }

  // 过滤 T -> boolean
  public Stream<T> filter(Predicate<T> predicate) {
    for (Iterator<T> it = datas.iterator(); it.hasNext();) {
      if (!predicate.test(it.next())) {
        it.remove();
      }
    }
    return this;
  }

  // 转换 T -> R
  public <R> Stream<R> map(Function<T, R> function) {
    List<R> datas = new ArrayList<R>();
    for (Iterator<T> it = this.datas.iterator(); it.hasNext();) {
      R r = function.apply(it.next());
      datas.add(r);
    }
    return new Stream<R>(datas);
  }

  // T -> void
  public void show(Consumer<T> consumer) {
    for (Iterator<T> it = this.datas.iterator(); it.hasNext();) {
      consumer.accept(it.next());
    }
  }

}
