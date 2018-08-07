package springboot.designpattern.responsibilityPattern;

public abstract class AbstractHandler implements Handler {
	
	public String name;
	
	private Handler handler;

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}