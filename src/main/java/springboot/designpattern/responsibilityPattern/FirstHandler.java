package springboot.designpattern.responsibilityPattern;

public class FirstHandler extends AbstractHandler{
	
	public FirstHandler(String name) {
		this.name = name;
	}

	@Override
	public void operator() {
		System.out.println(name + "  第一个handler");
		if (getHandler() != null) {
			getHandler().operator();
		}
	}
}