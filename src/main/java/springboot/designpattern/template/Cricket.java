package springboot.designpattern.template;

public class Cricket extends Game {

	@Override
	void endPlay() {
		System.out.println("板球运动结束");
	}

	@Override
	void initialize() {
		System.out.println("板球运动初始化，准备开始游戏");
	}

	@Override
	void startPlay() {
		System.out.println("板球游戏开始");
	}
}