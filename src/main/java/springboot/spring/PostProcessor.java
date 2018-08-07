package springboot.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 
 * PostProcessor.java
 * Description:bean后置处理器
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午4:04:59</p>
 * @author Tony
 * @version 1.0
 *
 */
public class PostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("后置处理器处理bean=【" + beanName + "】开始");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("后置处理器处理bean=【" + beanName + "】完毕!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
