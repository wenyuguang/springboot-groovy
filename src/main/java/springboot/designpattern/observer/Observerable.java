package springboot.designpattern.observer;

/**
 * 
 * Observerable.java
 * Description: 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 * <p>name: wen </p>
 * <p>Date:2018年8月17日 下午4:33:30</p>
 * @author Tony
 * @version 1.0
 *
 */
public interface Observerable {
    
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObserver();
    
}