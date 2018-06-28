package sample.springboot.filter.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerFilterConfiguration {

  @Bean
  public FilterRegistrationBean<MyFilter> logger() {
    FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<MyFilter>();
    MyFilter filter = new MyFilter();
    bean.setFilter(filter);
    List<String> urlPatterns = new ArrayList<String>();
    urlPatterns.add("/*");
    bean.setUrlPatterns(urlPatterns);
    return bean;
  }

  private class MyFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      Enumeration<String> names = request.getParameterNames();
      while (names.hasMoreElements()) {
        String name = names.nextElement();
        logger.info("{}={}", name, request.getParameter(name));
      }

      chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

  }

}
