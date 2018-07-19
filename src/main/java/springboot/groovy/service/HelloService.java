package springboot.groovy.service;

import org.springframework.stereotype.Component;

@Component
public class HelloService {
	public String hello() {
		return "now hello";
	}
}