package springboot.designpattern.responsibilityPattern;

public class ResponsibilityPatternTest {
	public static void main(String[] args) {
		MyHandler h1 = new MyHandler("张三");
		MyHandler h2 = new MyHandler("李四");
		MyHandler h3 = new MyHandler("王五");
		h1.setHandler(h2);
		h2.setHandler(h3);
		h1.operator();
	}
}