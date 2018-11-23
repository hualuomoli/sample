package sample.java8.function;

import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

// T -> boolean
public class PredicateTest {

  private Predicate<Integer> predicate;

  @Before
  public void before() {
    predicate = x -> x > 3;
  }

  @Test
  public void test() {
    boolean success = predicate.test(5);
    Assert.assertTrue(success);
  }

  // 增加一个条件判断
  @Test
  public void testAnd() {
    boolean success = predicate.and(x -> x < 10).test(8);
    Assert.assertTrue(success);
  }

  // 定义的验证或者新增的验证有一个通过就可
  @Test
  public void testOr() {
    boolean success = predicate.or(x -> x < -1).test(-2);
    Assert.assertTrue(success);
  }

  // 非
  @Test
  public void testNegate() {
    boolean success = predicate.negate().test(0);
    Assert.assertTrue(success);
  }
  
  

}
