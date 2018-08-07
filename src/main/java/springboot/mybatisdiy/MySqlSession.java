package springboot.mybatisdiy;

import java.lang.reflect.Proxy;

/**
 * 
 * sd.java
 * Description:
 * <p>name: wen </p>
 * <p>Date:2018年8月7日 下午5:22:09</p>
 * @author Tony
 * @version 1.0
 *
 */
public class MySqlSession {
	// 加载Mapper接口
	@SuppressWarnings("unchecked")
	public static <T> T getMapper(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new MyORMInvocationHandler());
	}
}