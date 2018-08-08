package springboot.designpattern.adapter;

public class Eat implements EatService {

	private EatAdapter eatAdapter;
	
	@Override
	public void eat(String type, String name) {
		if(type.equalsIgnoreCase("fruit")||type.equalsIgnoreCase("other")) {
			eatAdapter = new EatAdapter(type);
			eatAdapter.eat(type, name);
		}else{
			System.out.println("不想吃其他的东西");
		}
	}
}
