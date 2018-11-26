package sample.spring.expression.spel.operator;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 复杂操作
 */
public class ComplexExpressionTest {

  private static final Logger logger = LoggerFactory.getLogger(ComplexExpressionTest.class);

  // 实例方法
  @Test
  public void testInstanceMethod() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertEquals("JACK", parser.parseExpression("'jack'.toUpperCase()").getValue(String.class));
  }

  // 静态方法 T(class).method()
  @Test
  public void testStaticMethod() {
    ExpressionParser parser = new SpelExpressionParser();

    Double random = parser.parseExpression("T(java.lang.Math).random()").getValue(Double.class);
    logger.info("random is {}", random);
    Assert.assertNotNull(random);
  }

  // array
  @Test
  public void testArray() {
    ExpressionParser parser = new SpelExpressionParser();

    EvaluationContext context = new StandardEvaluationContext();
    String[] array = new String[] { "Java", "C", "PHP", "Nodejs", "Go" };
    context.setVariable("array", array);

    Expression expression = parser.parseExpression("I like #{#array[0]}.", new TemplateParserContext());
    Assert.assertEquals("I like Java.", expression.getValue(context, String.class));
  }

  // List集合,通过下标获取值
  @Test
  public void testList() {
    ExpressionParser parser = new SpelExpressionParser();

    EvaluationContext context = new StandardEvaluationContext();
    List<String> list = Lists.newArrayList("Java", "C", "PHP", "Nodejs", "Go");
    context.setVariable("list", list);

    Expression expression = parser.parseExpression("I like #{#list[0]}.", new TemplateParserContext());
    Assert.assertEquals("I like Java.", expression.getValue(context, String.class));
  }

  // Map通过key获取值
  @Test
  public void testMap() {
    ExpressionParser parser = new SpelExpressionParser();

    EvaluationContext context = new StandardEvaluationContext();
    Map<String, Object> map = Maps.newHashMap();
    map.put("username", "jack");
    map.put("age", 18);
    context.setVariable("map", map);

    String expressionString = "I'm name is #{#map.get('username')} with age #{#map.get('age')}.";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());
    Assert.assertEquals("I'm name is jack with age 18.", expression.getValue(context, String.class));
  }

  // instanceof
  @Test
  public void testInstanceof() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertTrue(parser.parseExpression("'abc' instanceof T(java.lang.String)").getValue(Boolean.class));
  }

  // matches
  @Test
  public void testMatches() {
    ExpressionParser parser = new SpelExpressionParser();

    String expressionString = "'2018-11-26 15:08:20' matches '^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$'";
    Assert.assertTrue(parser.parseExpression(expressionString).getValue(Boolean.class));
  }

  // 三目运算符
  @Test
  public void testTernary() {
    ExpressionParser parser = new SpelExpressionParser();

    Assert.assertEquals("Win", parser.parseExpression("2 > 1? 'Win': 'Lost'").getValue(String.class));
  }

  // 集合(符合条件第一个)
  @Test
  public void testSelectionFirst() {
    ExpressionParser parser = new SpelExpressionParser();

    EvaluationContext context = new StandardEvaluationContext();
    List<String> list = Lists.newArrayList("Java", "C", "PHP", "Nodejs", "Go");
    context.setVariable("list", list);

    String expressionString = "I like #{#list.^[#this.length() > 3]}.";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());
    Assert.assertEquals("I like Java.", expression.getValue(context, String.class));
  }

  // 集合(符合条件最后一个)
  @Test
  public void testSelectionLast() {
    ExpressionParser parser = new SpelExpressionParser();

    EvaluationContext context = new StandardEvaluationContext();
    List<String> list = Lists.newArrayList("Java", "C", "PHP", "Nodejs", "Go");
    context.setVariable("list", list);

    String expressionString = "I like #{#list.$[#this.length() > 3]}.";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());
    Assert.assertEquals("I like Nodejs.", expression.getValue(context, String.class));
  }

}
