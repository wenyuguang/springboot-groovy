package springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * ClassInfo.java
 * Description:适用类、接口（包括注解类型）或枚举  
 * <p>name: wen </p>
 * <p>Date:2018年8月6日 下午3:57:45</p>
 * @author Tony
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ClassInfo {
	String value();
}