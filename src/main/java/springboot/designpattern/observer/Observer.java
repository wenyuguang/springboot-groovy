package springboot.designpattern.observer;

/**
 * 
 * Observer.java
 * Description:抽象观察者
 * 定义了一个update()方法，当被观察者调用notifyObservers()方法时，观察者的update()方法会被回调。
 * <p>name: wen </p>
 * <p>Date:2018年8月17日 下午4:40:33</p>
 * @author Tony
 * @version 1.0
 *
 */
public interface Observer {

	public void update(String message);
}
