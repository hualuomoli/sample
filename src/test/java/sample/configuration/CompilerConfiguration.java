package sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sample.compiler.util.CustomBeanFactory;

@Configuration
public class CompilerConfiguration {

    @Autowired
    private DefaultListableBeanFactory beanFactory;
    @Autowired
    private ApplicationContext context;

    @Bean
    public CustomBeanFactory customBeanFactory() {
        return new CustomBeanFactory(beanFactory, context);
    }

}
