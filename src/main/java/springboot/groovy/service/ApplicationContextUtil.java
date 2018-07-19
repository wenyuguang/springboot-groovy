package springboot.groovy.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public Object getBean(Class<?> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	public Object getBean(String requiredType) {
		return applicationContext.getBean(requiredType);
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
