package sample.springboot.redis.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	private static StringRedisTemplate template;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		RedisUtils.context = applicationContext;
		template = context.getBean(StringRedisTemplate.class);
	}

	public static String get(String key) {
		String value = template.opsForValue().get(key);
		if (value == null || value.length() == 0) {
			return null;
		}
		return value;
	}

	public static void set(String key, String value) {
		if (value == null) {
			clean(key);
			return;
		}
		template.opsForValue().set(key, value);
	}

	public static void clean(String key) {
		template.opsForValue().set(key, "", 1, TimeUnit.MILLISECONDS);
	}

}
