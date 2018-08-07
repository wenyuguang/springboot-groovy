package springboot.designpattern.template;

public class Football extends Game {

	@Override
	void endPlay() {
		System.out.println("足球游戏结束");
	}

	@Override
	void initialize() {
		System.out.println("足球游戏初始化，准备开始游戏");
	}

	@Override
	void startPlay() {
		System.out.println("足球游戏开始");
	}
}