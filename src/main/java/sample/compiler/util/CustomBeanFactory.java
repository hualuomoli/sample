package sample.compiler.util;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class CustomBeanFactory {

    private final DefaultListableBeanFactory beanFactory;
    private final ApplicationContext context;
    private final List<Listener> listeners;

    public CustomBeanFactory(DefaultListableBeanFactory beanFactory, ApplicationContext context) {
        this.beanFactory = beanFactory;
        this.context = context;
        this.listeners = new ArrayList<Listener>();
    }

    public void register(String beanName, Class<?> clazz) {

        // remove
        if (beanFactory.containsBean(beanName)) {
            beanFactory.removeBeanDefinition(beanName);
        }

        // register
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        // load bean
        Object bean = this.getBean(clazz);

        // listen
        for (Listener listener : listeners) {
            listener.reload(bean, beanName, clazz);
        }

    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public static interface Listener {

        void reload(Object bean, String beanName, Class<?> clazz);

    }

}
