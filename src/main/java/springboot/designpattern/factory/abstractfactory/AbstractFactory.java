package springboot.designpattern.factory.abstractfactory;

import springboot.designpattern.factory.FactoryService;

public abstract class AbstractFactory {

	public abstract FactoryService sleep(String name);
	public abstract FactoryService sleep(Class<?> claz);
	public abstract FactoryService work(String name);
	public abstract FactoryService work(Class<?> claz);
}
