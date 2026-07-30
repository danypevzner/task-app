package d.pevzner.task_app;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import taskApp.TaskApplication;

@SpringBootTest(classes = TaskApplication.class)
class TaskAppApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(TaskAppApplicationTests.class);
	@Test
	void contextLoads() {
		logger.info("App context loaded!");
	}

}
