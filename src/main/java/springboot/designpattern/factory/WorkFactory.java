package springboot.designpattern.factory;

public class WorkFactory {
	
	private static volatile WorkFactory workFactory;
	
	public static WorkFactory newInstance(){
		synchronized (WorkFactory.class) {
			if(workFactory == null) {
				return new WorkFactory();
			}
		}
		return workFactory;
	}
	
	public FactoryService getService(String name) {
		if(name.equals("WorkA")) {
			return new WorkA();
		}else if(name.equals("WorkB")){
			return new WorkB();
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
