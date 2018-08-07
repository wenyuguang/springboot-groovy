package springboot.designpattern.factory;

public class WorkB implements FactoryService {

	@Override
	public void doSomething() {
		System.out.println("B 工作中");
	}

}
