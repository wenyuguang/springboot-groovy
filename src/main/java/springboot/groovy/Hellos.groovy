package springboot.groovy

import org.springframework.beans.factory.annotation.Autowired

import springboot.groovy.service.Hello
import springboot.groovy.service.HelloService

class Hellos implements Hello{
	
    @Autowired
    HelloService service;

    HelloService getService() {
        return service
    }

	@Override
    def run() {
        print(service.hello())
    }
}