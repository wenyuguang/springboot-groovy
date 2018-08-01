package springboot.aop.cglib;

public class CglibServiceImpl implements CglibService {

	@Override
	public void test() {
		System.out.println("come in test method");
	}
}
