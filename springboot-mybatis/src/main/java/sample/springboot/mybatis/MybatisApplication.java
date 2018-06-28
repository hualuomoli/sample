package sample.springboot.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sample.springboot.mybatis.plugin.mybatis.dialect.db.MySQLDialect;
import sample.springboot.mybatis.plugin.mybatis.interceptor.pagination.PaginationInterceptor;

@SpringBootApplication
public class MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}

	@Bean
	public Interceptor getInterceptor() {
		PaginationInterceptor interceptor = new PaginationInterceptor();
		interceptor.setDialect(new MySQLDialect());
		return interceptor;
	}

}
