package springboot.designpattern.factory.abstractfactory;

import springboot.designpattern.factory.FactoryService;
import springboot.designpattern.factory.WorkA;
import springboot.designpattern.factory.WorkB;
import springboot.designpattern.factory.WorkC;

public class WorkFactory extends AbstractFactory{

	@Override
	public FactoryService sleep(String name) {
		return null;
	}

	@Override
	public FactoryService work(String name) {
		if(name.toLowerCase().equals("worka")) {
			return new WorkA();
		}else if(name.toLowerCase().equals("workb")) {
			return new WorkB();
		}else if(name.toLowerCase().equals("workc")) {
			return new WorkC();
		}
		return null;
	}

	@Override
	public FactoryService sleep(Class<?> claz) {
		return null;
	}

	@Override
	public FactoryService work(Class<?> claz) {
		FactoryService factoryService = null;
		try {
			factoryService = (FactoryService)Class.forName(claz.getName()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return factoryService;
	}
}
