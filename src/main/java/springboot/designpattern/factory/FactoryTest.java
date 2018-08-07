package springboot.designpattern.factory;

public class FactoryTest {

	public static void main(String[] args) {
		WorkFactory work = WorkFactory.newInstance();
		work.getService("WorkA").doSomething();
		work.getService("WorkB").doSomething();
		
		WorkFactory.getClass(WorkA.class).doSomething();
		WorkFactory.getClass(WorkB.class).doSomething();
	}
}
