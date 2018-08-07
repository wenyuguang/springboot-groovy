package springboot.designpattern.factory.abstractfactory;

public class OneAbstractFactory {

	public AbstractFactory newInstance(String type) {
		if(type.toLowerCase().equals("sleep")) {
			return new SleepFactory();
		}else if(type.toLowerCase().equals("work")) {
			return new WorkFactory();
		}
		return null;
	}
	
	public AbstractFactory newInstance(Class<?> claz) {
		AbstractFactory abstractFactory = null;
		try {
			abstractFactory = (AbstractFactory)Class.forName(claz.getName()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return abstractFactory;
	}
}
