package springboot.designpattern.adapter;

public class EatAdapter implements EatService {

	private EdvanceEatService edvanceEatService;
	
	EatAdapter(String type){
		if(type.equals("fruit")) {
			edvanceEatService = new EatFruitServiceImpl();
		}else if(type.equals("other")) {
			edvanceEatService = new EatOtherServiceImpl();
		}
	}
	
	@Override
	public void eat(String type, String name) {
		if(type.equals("fruit")) {
			edvanceEatService.eatFruit(name);
		}else if(type.equals("other")) {
			edvanceEatService.eatOther(name);
		}

	}

}
