package springboot.designpattern.responsibilityPattern;

public class ResponsibilityPatternTest {
	public static void main(String[] args) {
		AbstractHandler h1 = new FirstHandler("张三");
		AbstractHandler h2 = new FirstHandler("李四");
		AbstractHandler h3 = new FirstHandler("王五");
		AbstractHandler h4 = new FirstHandler("马六");
		h1.setHandler(h2);
		h2.setHandler(h3);
		h3.setHandler(h4);
		h1.operator();
	}
}