package springboot.designpattern.factory;

public class SleepC implements FactoryService {

	@Override
	public void doSomething() {
		System.out.println("C 睡觉中");
	}

}
