package springboot.designpattern.factory;

public class SleepB implements FactoryService {

	@Override
	public void doSomething() {
		System.out.println("B 睡觉中");
	}

}
