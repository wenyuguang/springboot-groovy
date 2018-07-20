package springboot.groovy;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
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
		long start = System.currentTimeMillis();
		ApplicationContext applicationContext = applicationContextUtil.getApplicationContext();
		try {
			Hello clz1 = (Hello) applicationContext.getBean("hello");
			System.out.println();
		}catch(Exception e) {
			
		}
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
		GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
		Class<?> clazz = groovyClassLoader.parseClass(scriptContent);
		System.err.println(clazz.getName());
		try {
			groovyClassLoader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(clazz).getRawBeanDefinition();
		applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(beanDefinition, "hello");
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();

		beanFactory.registerBeanDefinition("hello", beanDefinition);
		Hello clz = (Hello) applicationContext.getBean("hello");
		clz.run();
		long end = System.currentTimeMillis();
		return end-start;
	}
}
