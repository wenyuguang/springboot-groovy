package springboot.designpattern.factory;

public class WorkC implements FactoryService {

	@Override
	public void doSomething() {
		System.out.println("C 工作中");
	}

}
