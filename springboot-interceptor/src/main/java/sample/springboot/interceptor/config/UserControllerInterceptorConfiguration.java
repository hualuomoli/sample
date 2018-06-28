package sample.springboot.interceptor.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 1、实现WebMvcConfigurer接口 
@Configuration
public class UserControllerInterceptorConfiguration implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(UserControllerInterceptorConfiguration.class);

  // 重写addInterceptors方法
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    HandlerInterceptor interceptor = new MyInterceptor();
    registry.addInterceptor(interceptor).addPathPatterns("/user/**").excludePathPatterns("/user/login");
  }

  private class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      logger.info("用户模块被调用");

      // 验证token
      String token = request.getHeader("token");
      if (token == null) {
        token = request.getParameter("token");
      }

      // 未登录
      if (token == null || token.length() == 0) {
        response.setStatus(401);
        response.getWriter().println("please login first.");
        return false;
      }

      return true;
    }

  }

}
