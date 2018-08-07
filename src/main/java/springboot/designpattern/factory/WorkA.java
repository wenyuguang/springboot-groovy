package springboot.designpattern.factory;

public class WorkA implements FactoryService {

	@Override
	public void doSomething() {
		System.out.println("A 工作中");
	}

}
