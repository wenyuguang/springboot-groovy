package springboot.designpattern.adapter;

public class EatOtherServiceImpl implements EdvanceEatService {

	@Override
	public void eatFruit(String name) {

	}

	@Override
	public void eatOther(String name) {
		System.out.println("吃其他东西了");
	}
}
