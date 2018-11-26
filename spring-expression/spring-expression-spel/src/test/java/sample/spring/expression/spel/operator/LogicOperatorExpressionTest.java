package sample.spring.expression.spel.operator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * 逻辑操作符
 */
public class LogicOperatorExpressionTest {

  @Test
  public void test() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertTrue(parser.parseExpression("1 == 1 and 5 > 3").getValue(Boolean.class)); // and 
    Assert.assertTrue(parser.parseExpression("1 == 0 or 5 > 3").getValue(Boolean.class)); // or
    Assert.assertTrue(parser.parseExpression("not (1 == 0)").getValue(Boolean.class)); // not
    Assert.assertTrue(parser.parseExpression("!(1 == 0)").getValue(Boolean.class)); // not
  }

}
