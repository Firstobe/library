package org.example.librarysystem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {
	/*
	 * @Author cms
	 * @Description //TODO $
	 * @Date $ $
	 * @Param $
	 * @return $
	 **/
	private static final Logger log = LoggerFactory.getLogger(SpringContextHolder.class);
	private static ApplicationContext applicationContext;

	public SpringContextHolder() {
	}
 @Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		if (log.isInfoEnabled()) {
			log.info("SpringContextHolder setApplicationContext");
		}

		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		assertApplicationContext();
		return applicationContext;
	}

	public static <T> Collection<T> getBeansOfType(Class<T> clazz) {
		assertApplicationContext();
		return applicationContext.getBeansOfType(clazz).values();
	}

	public static <T> T getBean(Class<T> clazz) {
		assertApplicationContext();
		return applicationContext.getBean(clazz);
	}

	public static Object  getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

	private static void assertApplicationContext() {
		if (applicationContext == null) {
			throw new RuntimeException("applicationContext 属性为 null,请检查是否注入了SpringContextHolder!");
		}
	}

	public static void publishEvent(ApplicationEvent event) {
		if (applicationContext != null) {
			try {
				applicationContext.publishEvent(event);
			} catch (Exception var2) {
				log.error(var2.getMessage());
			}

		}
	}

	public static void clearHolder() {
		if (log.isDebugEnabled()) {
			log.debug("清除SpringContextHolder中的ApplicationContext:{}", applicationContext);
		}

		applicationContext = null;
	}

	public void destroy() {
		try {
			clearHolder();
		} catch (Throwable var2) {
			throw var2;
		}
	}
}
