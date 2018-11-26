package sample.spring.expression.spel.operator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * 关系操作符
 */
public class RelationOperatorExpressionTest {

  @Test
  public void test() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertTrue(parser.parseExpression("1 == 1").getValue(Boolean.class)); // eq
    Assert.assertTrue(parser.parseExpression("1 != 2").getValue(Boolean.class)); // ne
    Assert.assertTrue(parser.parseExpression("1 < 2").getValue(Boolean.class)); // lt
    Assert.assertTrue(parser.parseExpression("1 > 0").getValue(Boolean.class)); // gt
    Assert.assertTrue(parser.parseExpression("1 >= 1").getValue(Boolean.class)); // ge
    Assert.assertTrue(parser.parseExpression("1 >= 0").getValue(Boolean.class)); // ge
    Assert.assertTrue(parser.parseExpression("1 <= 1").getValue(Boolean.class)); // le
    Assert.assertTrue(parser.parseExpression("1 <= 2").getValue(Boolean.class)); // le

    Assert.assertTrue(parser.parseExpression("1 eq 1").getValue(Boolean.class)); // eq
    Assert.assertTrue(parser.parseExpression("1 ne 2").getValue(Boolean.class)); // ne
    Assert.assertTrue(parser.parseExpression("1 lt 2").getValue(Boolean.class)); // lt
    Assert.assertTrue(parser.parseExpression("1 gt 0").getValue(Boolean.class)); // gt
    Assert.assertTrue(parser.parseExpression("1 ge 1").getValue(Boolean.class)); // ge
    Assert.assertTrue(parser.parseExpression("1 ge 0").getValue(Boolean.class)); // ge
    Assert.assertTrue(parser.parseExpression("1 le 1").getValue(Boolean.class)); // le
    Assert.assertTrue(parser.parseExpression("1 le 2").getValue(Boolean.class)); // le
    Assert.assertTrue(parser.parseExpression("5 between {0, 10}").getValue(Boolean.class)); // between
  }

  // 类型不匹配
  @Test
  public void testTypeNotMatch() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertTrue(!parser.parseExpression("1 == '1'").getValue(Boolean.class));
    Assert.assertTrue(parser.parseExpression("1 != '1'").getValue(Boolean.class));
  }

}
