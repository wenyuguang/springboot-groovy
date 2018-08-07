package springboot.designpattern.factory;

public class SleepA implements FactoryService {

	@Override
	public void doSomething() {
		System.out.println("A 睡觉中");
	}

}
