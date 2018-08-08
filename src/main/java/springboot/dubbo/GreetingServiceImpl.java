package springboot.dubbo;

public class GreetingServiceImpl implements GreetingService {
	public String sayHello(String name) {
		return "Hello " + name;
	}
}