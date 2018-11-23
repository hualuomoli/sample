package sample.spring.core.discoverer;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import sample.spring.core.discoverer.service.UserService;

/**
 * 方法获取参数名
 */
public class MethodParameterNamesTest {

  private static ParameterNameDiscoverer discoverer;

  @BeforeClass
  public static void beforeClass() {
    discoverer = new DefaultParameterNameDiscoverer();
  }

  @Test
  public void test() throws Exception {
    Method method = UserService.class.getMethod("login", new Class[] { String.class, String.class });
    String[] names = discoverer.getParameterNames(method);
    Assert.assertNotNull(names);
    Assert.assertEquals(2, names.length);
    Assert.assertEquals("username", names[0]);
    Assert.assertEquals("password", names[1]);
  }

}
