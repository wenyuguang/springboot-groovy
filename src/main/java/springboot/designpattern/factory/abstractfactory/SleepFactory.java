package springboot.designpattern.factory.abstractfactory;

import springboot.designpattern.factory.FactoryService;
import springboot.designpattern.factory.SleepA;
import springboot.designpattern.factory.SleepB;
import springboot.designpattern.factory.SleepC;

public class SleepFactory extends AbstractFactory{

	@Override
	public FactoryService sleep(String name) {
		if(name.toLowerCase().equals("sleepa")) {
			return new SleepA();
		}else if(name.toLowerCase().equals("sleepB")) {
			return new SleepB();
		}else if(name.toLowerCase().equals("sleepc")) {
			return new SleepC();
		}
		return null;
	}

	@Override
	public FactoryService work(String name) {
		
		return null;
	}

	@Override
	public FactoryService sleep(Class<?> claz) {
		FactoryService factoryService = null;
		try {
			factoryService = (FactoryService)Class.forName(claz.getName()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return factoryService;
	}

	@Override
	public FactoryService work(Class<?> claz) {
		// TODO Auto-generated method stub
		return null;
	}
}
