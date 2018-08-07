package springboot.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
/**
 * 
 * FactoryPostProcessor.java
 * Description:BeanFactoryPostProcessor的回调比BeanPostProcessor要早,
 *             BeanFactoryPostProcessor可以修改BEAN的配置信息而BeanPostProcessor不能
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午4:05:04</p>
 * @author Tony
 * @version 1.0
 *
 */
public class FactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("******调用了BeanFactoryPostProcessor");
		String[] beanStr = beanFactory.getBeanDefinitionNames();
		for (String beanName : beanStr) {
			if ("beanFactoryPostProcessorTest".equals(beanName)) {
				BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
				MutablePropertyValues m = beanDefinition.getPropertyValues();
				if (m.contains("name")) {
					m.addPropertyValue("name", "赵四");
					System.out.println("》》》修改了name属性初始值了");
				}
			}
		}
	}

}
