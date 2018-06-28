package sample.springboot.filter.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenFilterConfiguration {

  @Bean
  public FilterRegistrationBean<MyFilter> logger() {
    FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<MyFilter>();
    MyFilter filter = new MyFilter();
    bean.setFilter(filter);
    List<String> urlPatterns = new ArrayList<String>();
    urlPatterns.add("/api/*");
    bean.setUrlPatterns(urlPatterns);
    return bean;
  }

  private class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse res = (HttpServletResponse) response;

      String token = req.getHeader("token");
      if (token == null) {
        token = req.getParameter("token");
      }

      // 未登录
      if (token == null || token.length() == 0) {
        res.setStatus(401);
        res.getWriter().println("please login first.");
        return;
      }

      // 已登录
      chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

  }

}
