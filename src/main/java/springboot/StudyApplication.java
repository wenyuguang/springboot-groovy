package springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class StudyApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}
	
	@Component
	public class TestLog implements CommandLineRunner{
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		@Override
		public void run(String... args) throws Exception {
			logger.info("这是一个info日志");
			logger.debug("这是一个info日志");
			logger.warn("这是一个info日志");
			logger.error("这是一个info日志");
			logger.trace("这是一个info日志");
		}
		
	}
}