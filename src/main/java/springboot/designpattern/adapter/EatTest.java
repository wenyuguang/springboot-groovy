package springboot.designpattern.adapter;

public class EatTest {

	public static void main(String[] args) {
		Eat eat = new Eat();
		eat.eat("fruit", "苹果");
		eat.eat("other", "今晚上吃鱼");
	}
}
