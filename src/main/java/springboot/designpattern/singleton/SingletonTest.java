package springboot.designpattern.singleton;

public class SingletonTest {

	private static volatile SingletonTest singletonTest;
	
	public static SingletonTest newInstance() {
		synchronized (SingletonTest.class) {
			if(singletonTest == null) {
				singletonTest = new SingletonTest();
			}
			return singletonTest;
		}
	}
	public static void main(String[] args) {
		SingletonTest.newInstance();
	}
}
