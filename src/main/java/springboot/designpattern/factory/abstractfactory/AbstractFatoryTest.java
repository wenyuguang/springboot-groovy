package springboot.designpattern.factory.abstractfactory;

import springboot.designpattern.factory.SleepA;
import springboot.designpattern.factory.WorkA;

public class AbstractFatoryTest {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		new OneAbstractFactory().newInstance("sleep").sleep("SleepA").doSomething();
		new OneAbstractFactory().newInstance("work").work("worka").doSomething();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
		
		start = System.currentTimeMillis();
		new OneAbstractFactory().newInstance(SleepFactory.class).sleep(SleepA.class).doSomething();
		new OneAbstractFactory().newInstance(WorkFactory.class).work(WorkA.class).doSomething();
		end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
