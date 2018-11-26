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
 * 动态参数
 */
public class DynamicExpressionTest {

  // 根据实体类的getter方法解析模板
  @Test
  public void testParseByObject() {
    ExpressionParser parser = new SpelExpressionParser();

    // 表达式数据
    User user = new User("jack", "杰克", 18, 'M', 66666L);

    // 获取解析表达式
    String expressionString = "my name is #{username}, nickname is #{nickname}, age is #{age}, gender is #{gender}, salary is #{salary}";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());

    // 获取值
    String value = expression.getValue(user, String.class);
    Assert.assertEquals("my name is jack, nickname is 杰克, age is 18, gender is M, salary is 66666", value);
  }

  // 根据上下文属性解析模板
  @Test
  public void testParseByContext() {
    ExpressionParser parser = new SpelExpressionParser();

    // 上下文
    EvaluationContext context = new StandardEvaluationContext();
    context.setVariable("username", "jack");
    context.setVariable("nickname", "杰克");
    context.setVariable("age", 18);
    context.setVariable("gender", 'M');
    context.setVariable("salary", 66666L);

    // 获取解析表达式
    String expressionString = "my name is #{#username}, nickname is #{#nickname}, age is #{#age}, gender is #{#gender}, salary is #{#salary}";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());

    // 获取值
    String value = expression.getValue(context, String.class);
    Assert.assertEquals("my name is jack, nickname is 杰克, age is 18, gender is M, salary is 66666", value);
  }

  // 根据属性上下文解析实体类模板
  @Test
  public void testParseByContextWithObject() {
    ExpressionParser parser = new SpelExpressionParser();

    // 上下文
    EvaluationContext context = new StandardEvaluationContext();
    User user = new User("jack", "杰克", 18, 'M', 66666L);
    context.setVariable("user", user);

    // 获取解析表达式
    String expressionString = "my name is #{#user.username}, nickname is #{#user.nickname}, age is #{#user.age}, gender is #{#user.gender}, salary is #{#user.salary}";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());

    // 获取值
    String value = expression.getValue(context, String.class);
    Assert.assertEquals("my name is jack, nickname is 杰克, age is 18, gender is M, salary is 66666", value);
  }

  // 根据属性上下文解析实体类方法模板
  @Test
  public void testParseByContextWithObjectMethod() {
    ExpressionParser parser = new SpelExpressionParser();

    // 上下文
    EvaluationContext context = new StandardEvaluationContext();
    User user = new User("jack", "杰克", 18, 'M', 66666L);
    context.setVariable("user", user);

    // 获取解析表达式
    String expressionString = "my name is #{#user.getUsername()}, nickname is #{#user.getNickname()}, age is #{#user.getAge()}, gender is #{#user.getGender()}, salary is #{#user.getSalary()}";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());

    // 获取值
    String value = expression.getValue(context, String.class);
    Assert.assertEquals("my name is jack, nickname is 杰克, age is 18, gender is M, salary is 66666", value);
  }

  // 安全操作(防止空指针,在需要操作的属性上[.]之前增加?)
  @Test
  public void testParseInSecurity() {
    ExpressionParser parser = new SpelExpressionParser();

    // 获取解析表达式
    String expressionString = "my name is #{#user?.username?.toUpperCase()}, nickname is #{#user.nickname}, age is #{#user.age}, gender is #{#user.gender}, salary is #{#user.salary}";
    Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());

    // 属性有值
    EvaluationContext context = new StandardEvaluationContext();
    User user = new User(null, "杰克", 18, 'M', 66666L);
    context.setVariable("user", user);
    String value = expression.getValue(context, String.class);
    Assert.assertEquals("my name is , nickname is 杰克, age is 18, gender is M, salary is 66666", value);
  }

  @SuppressWarnings("unused")
  private class User {

    /** 用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 年龄 */
    private Integer age;
    /** 性别 */
    private char gender;
    /** 工资 */
    private Long salary;

    private User(String username, String nickname, Integer age, char gender, Long salary) {
      this.username = username;
      this.nickname = nickname;
      this.age = age;
      this.gender = gender;
      this.salary = salary;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public Integer getAge() {
      return age;
    }

    public void setAge(Integer age) {
      this.age = age;
    }

    public char getGender() {
      return gender;
    }

    public void setGender(char gender) {
      this.gender = gender;
    }

    public Long getSalary() {
      return salary;
    }

    public void setSalary(Long salary) {
      this.salary = salary;
    }

    @Override
    public String toString() {
      return "User [username=" + username + ", nickname=" + nickname + ", age=" + age + ", gender=" + gender
          + ", salary=" + salary + "]";
    }

    // end class User
  }

}
