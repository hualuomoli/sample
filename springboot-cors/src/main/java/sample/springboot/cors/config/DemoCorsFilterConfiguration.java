package sample.springboot.cors.config;

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
public class DemoCorsFilterConfiguration {

  @Bean
  public FilterRegistrationBean<MyFilter> logger() {
    FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<MyFilter>();
    MyFilter filter = new MyFilter();
    bean.setFilter(filter);
    List<String> urlPatterns = new ArrayList<String>();
    urlPatterns.add("/demo/*");
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

      // 服务端增加跨域信息
      //带cookie的时候，origin必须是全匹配，不能使用*
      String origin = req.getHeader("origin");
      if (!org.springframework.util.StringUtils.isEmpty(origin)) {
        res.addHeader("Access-Control-Allow-Origin", origin);
      }

      // 支持的方法
      res.addHeader("Access-Control-Allow-Methods", "*");

      // 支持所有自定义头
      String headers = req.getHeader("Access-Control-Request-Headers");
      if (!org.springframework.util.StringUtils.isEmpty(headers)) {
        res.addHeader("Access-Control-Allow-Headers", headers);
      }
      
      // 缓存时间
      res.addHeader("Access-Control-Max-Age", "3600");

      // 允许使用cookie
      res.addHeader("Access-Control-Allow-Credentials", "true");

      // next
      chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
  }

}
