package sample.spring.expression.spel.operator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * 数学操作符
 */
public class MathOperatorExpressionTest {

  @Test
  public void test() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertEquals(2, parser.parseExpression("1 + 1").getValue(Integer.class).intValue());
    Assert.assertEquals(1, parser.parseExpression("3 - 2").getValue(Integer.class).intValue());
    Assert.assertEquals(30, parser.parseExpression("5 * 6").getValue(Integer.class).intValue());
    Assert.assertEquals(5, parser.parseExpression("20 / 4").getValue(Integer.class).intValue());
    Assert.assertEquals(6, parser.parseExpression("20 / 3").getValue(Integer.class).intValue()); // 取整
    Assert.assertEquals(2, parser.parseExpression("20 % 3").getValue(Integer.class).intValue());
    Assert.assertEquals(0, parser.parseExpression("20 % 4").getValue(Integer.class).intValue());
    Assert.assertEquals(1024, parser.parseExpression("2 ^ 10").getValue(Integer.class).intValue());

    Assert.assertEquals(6, parser.parseExpression("20 div 3").getValue(Integer.class).intValue()); // 取整
    Assert.assertEquals(2, parser.parseExpression("20 mod 3").getValue(Integer.class).intValue());
  }

}
