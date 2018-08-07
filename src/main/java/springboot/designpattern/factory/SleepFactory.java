package springboot.designpattern.factory;

public class SleepFactory {
	
	private static volatile SleepFactory sleepFactory;
	
	public static SleepFactory newInstance(){
		synchronized (SleepFactory.class) {
			if(sleepFactory == null) {
				return new SleepFactory();
			}
		}
		return sleepFactory;
	}
	
	public FactoryService getService(String name) {
		if(name.equals("SleepA")) {
			return new SleepA();
		}else if(name.equals("SleepB")){
			return new SleepB();
		}
		else if(name.equals("SleepC")){
			return new SleepC();
		}
		return null;
	}

	public static FactoryService getClass(Class<? extends FactoryService> clz) {
		FactoryService t = null;
		try {
			t = (FactoryService) Class.forName(clz.getName()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}
}
