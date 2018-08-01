package springboot.aop.cglib;

/**
 * 
 * CglibMain.java
 * Description: cglib代理
 * <p>name: wen </p>
 * <p>Date:2018年8月1日 下午4:40:01</p>
 * @author Tony
 * @version 1.0
 *
 */
public class CglibMain {
    public static void main(String[] args) {
    	CglibProxy cglibProxy = new CglibProxy();
    	CglibService cglibService = cglibProxy.getProxy(CglibServiceImpl.class);
    	cglibService.test();
    }
}