package springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * MethodInfo.java
 * Description:适用方法  
 * <p>name: wen </p>
 * <p>Date:2018年8月6日 下午3:58:08</p>
 * @author Tony
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodInfo {
	String name() default "long";

	String data();

	int age() default 27;
}