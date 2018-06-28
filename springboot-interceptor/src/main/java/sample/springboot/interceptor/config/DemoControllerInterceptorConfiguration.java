package sample.springboot.interceptor.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 1、实现WebMvcConfigurer接口 
@Configuration
public class DemoControllerInterceptorConfiguration implements WebMvcConfigurer {

  private static final Logger logger = LoggerFactory.getLogger(DemoControllerInterceptorConfiguration.class);

  // 重写addInterceptors方法
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    HandlerInterceptor interceptor = new MyInterceptor();
    registry.addInterceptor(interceptor).addPathPatterns("/demo/**").excludePathPatterns("/demo/deal");
  }

  private class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      logger.info("---------------------开始进入请求地址拦截----------------------------");
      Enumeration<String> names = request.getParameterNames();
      while (names.hasMoreElements()) {
        String name = names.nextElement();
        logger.debug("{}={}", name, request.getParameter(name));
      }

      return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      logger.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      logger.info("---------------视图渲染之后的操作-------------------------");
    }

  }

}
