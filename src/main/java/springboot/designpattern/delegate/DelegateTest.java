package springboot.designpattern.delegate;

public class DelegateTest {

	public static void main(String[] args) {
		Manage mg = new Manage(new AExecutorImpl());
		mg.doSomething();
	}

}
