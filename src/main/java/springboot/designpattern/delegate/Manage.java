package springboot.designpattern.delegate;

public class Manage implements IExecutor {

	private IExecutor iExecutor;
	
	Manage(IExecutor iExecutor){
		this.iExecutor = iExecutor;
	}
	@Override
	public void doSomething() {
		this.iExecutor.doSomething();
	}
}
