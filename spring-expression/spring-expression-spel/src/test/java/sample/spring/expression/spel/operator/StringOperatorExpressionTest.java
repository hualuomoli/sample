package sample.spring.expression.spel.operator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * 字符串操作符
 */
public class StringOperatorExpressionTest {

  @Test
  public void test() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertEquals("Hello world!", parser.parseExpression("'Hello' + ' ' + 'world' + '!'").getValue(String.class));
    Assert.assertEquals("JACK", parser.parseExpression("'jack'.toUpperCase()").getValue(String.class));
  }

}
