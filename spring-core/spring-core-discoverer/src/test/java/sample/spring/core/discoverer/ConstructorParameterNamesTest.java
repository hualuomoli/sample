package sample.spring.core.discoverer;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import sample.spring.core.discoverer.entity.Gender;
import sample.spring.core.discoverer.entity.User;

/**
 * 构造器获取参数名
 */
public class ConstructorParameterNamesTest {

  private static ParameterNameDiscoverer discoverer;

  @BeforeClass
  public static void beforeClass() {
    discoverer = new DefaultParameterNameDiscoverer();
  }

  // 无参构造器 new User()
  @Test
  public void testNoParametersTest() throws Throwable {
    Constructor<User> constructor = User.class.getConstructor(new Class[0]);
    String[] names = discoverer.getParameterNames(constructor);
    Assert.assertNotNull(names);
    Assert.assertEquals(0, names.length);
  }

  // 全部参数构造器 new User(String, String, Integer, Gender)
  @Test
  public void testFullParametersTest() throws Exception {
    Constructor<User> constructor = User.class.getConstructor(new Class[] { String.class, String.class, Integer.class, Gender.class });
    String[] names = discoverer.getParameterNames(constructor);
    Assert.assertNotNull(names);
    Assert.assertEquals(4, names.length);
    Assert.assertEquals("username", names[0]);
    Assert.assertEquals("nickname", names[1]);
    Assert.assertEquals("age", names[2]);
    Assert.assertEquals("gender", names[3]);
  }

}
