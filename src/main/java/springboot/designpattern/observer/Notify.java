package springboot.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Notify implements Observerable {

	//注意到这个List集合的泛型参数为Observer接口，设计原则：面向接口编程而不是面向实现编程
    private List<Observer> list;
    private String message;
    
    public Notify() {
        list = new ArrayList<Observer>();
    }
    
	@Override
	public void registerObserver(Observer o) {
		list.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		list.remove(o);
	}

	@Override
	public void notifyObserver() {
		for(int i = 0; i < list.size(); i++) {
            Observer oserver = list.get(i);
            oserver.update(message);
        }
	}
	
	public void setInfomation(String s) {
        this.message = s;
        System.out.println("消息推送： " + s);
        //消息更新，通知所有观察者
        notifyObserver();
    }
}
