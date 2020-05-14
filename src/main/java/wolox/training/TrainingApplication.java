package wolox.training;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TrainingApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrainingApplication.class, args);
		String threadId = String.valueOf(Thread.currentThread().getId());
		ThreadContext.put("TId", threadId);
	}

}
