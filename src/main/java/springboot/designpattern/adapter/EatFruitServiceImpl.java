package springboot.designpattern.adapter;

public class EatFruitServiceImpl implements EdvanceEatService {

	@Override
	public void eatFruit(String name) {
		System.out.println("吃水果了");
	}

	@Override
	public void eatOther(String name) {
		// TODO Auto-generated method stub

	}
}
