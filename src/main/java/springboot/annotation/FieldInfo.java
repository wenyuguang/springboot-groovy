package springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * FieldInfo.java
 * Description:适用field属性，也包括enum常量  
 * <p>name: wen </p>
 * <p>Date:2018年8月6日 下午3:57:34</p>
 * @author Tony
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldInfo {
	int[] value();
}