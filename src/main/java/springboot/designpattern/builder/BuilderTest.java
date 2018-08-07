package springboot.designpattern.builder;

public class BuilderTest {

	public static void main(String[] args) {
		Builder builder = new ConcreteBuilder();
		Director director = new Director();
		Product pro = director.builder(builder);
		System.out.println(pro);
		
		Product pro1 = new Director().builder(new ConcreteBuilder());
		System.out.println(pro1);
	}
}
