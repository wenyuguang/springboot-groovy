package springboot.aop.jdk;

import java.lang.reflect.Proxy;

/**
 * 
 * AopMain.java
 * Description:jdk代理
 * <p>name: wen </p>
 * <p>Date:2018年8月1日 下午4:40:14</p>
 * @author Tony
 * @version 1.0
 *
 */
public class AopMain {
    public static void main(String[] args) {
    	AopService aopService = new AopServiceImpl();
    	AopHandler handle = new AopHandler(aopService);
    	AopService i = (AopService) Proxy.newProxyInstance(
    			AopServiceImpl.class.getClassLoader(), new Class[] { AopService.class }, handle);
        i.test();
    }
}