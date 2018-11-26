package sample.spring.expression.spel.operator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 默认值
 */
public class ElvisExpressionTest {

  @Test
  public void test() {
    ExpressionParser parser = new SpelExpressionParser();

    Expression expression = parser.parseExpression("#{#server?:'localhost'}", new TemplateParserContext());

    // 设置了参数
    EvaluationContext context = new StandardEvaluationContext();
    context.setVariable("server", "192.168.1.100");
    Assert.assertEquals("192.168.1.100", expression.getValue(context, String.class));

    // 没有设置参数
    context = new StandardEvaluationContext();
    Assert.assertEquals("localhost", expression.getValue(context, String.class));
  }

}
