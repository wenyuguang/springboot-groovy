package springboot.groovy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import groovy.lang.GroovyClassLoader;
import springboot.groovy.service.ApplicationContextUtil;
import springboot.groovy.service.Hello;

@RestController
public class TestController {
	
	@Autowired
	private ApplicationContextUtil applicationContextUtil;

	@GetMapping("/test")
	public Object test() {
		String scriptContent = "import org.springframework.beans.factory.annotation.Autowired\r\n" + 
				"import springboot.groovy.service.Hello\r\n" + 
				"import springboot.groovy.service.HelloService\r\n" + 
				"class Hello implements Hello{\r\n" + 
				"	\r\n" + 
				"    @Autowired\r\n" + 
				"    HelloService service;\r\n" + 
				"\r\n" + 
				"    HelloService getService() {\r\n" + 
				"        return service\r\n" + 
				"    }\r\n" + 
				"\r\n" + 
				"	@Override\r\n" + 
				"    def run() {\r\n" + 
				"        print(service.hello())\r\n" + 
				"    }\r\n" + 
				"}";
		Class<?> clazz = new GroovyClassLoader().parseClass(scriptContent);
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
		applicationContextUtil.getApplicationContext().getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(beanDefinition, "hello");
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContextUtil.getApplicationContext().getAutowireCapableBeanFactory();

		beanFactory.registerBeanDefinition("hello", beanDefinition);
		Hello clz = (Hello) applicationContextUtil.getApplicationContext().getBean("hello");
		return clz.run();
	}
}
