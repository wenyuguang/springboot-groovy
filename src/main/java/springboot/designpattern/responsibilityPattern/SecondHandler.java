package springboot.designpattern.responsibilityPattern;

public class SecondHandler extends AbstractHandler{
	
	public SecondHandler(String name) {
		this.name = name;
	}

	@Override
	public void operator() {
		System.out.println(name + "  第二个handler");
		if (getHandler() != null) {
			getHandler().operator();
		}
	}
}