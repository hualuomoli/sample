package sample.spring.expression.spel.operator;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 自定义方法
 */
public class FunctionExpressionTest {

  @Test
  public void test() throws Exception {
    ExpressionParser parser = new SpelExpressionParser();

    StandardEvaluationContext context = new StandardEvaluationContext();
    Method method = FunctionExpressionTest.class.getDeclaredMethod("length", new Class[] { String.class });
    context.registerFunction("length", method);

    Assert.assertEquals(3, parser.parseExpression("#length('tom')").getValue(context, Integer.class).intValue());
  }

  /**
   * 字符串床都
   * 
   * @param str 字符串
   * @return 如果字符串为空,返回0.否则返回字符串长度
   */
  public static int length(String str) {
    return str == null ? 0 : str.length();
  }

}
