package d.pevzner.task_app;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import taskApp.TaskApplication;
import taskApp.exception.TaskNotFoundException;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;
import taskApp.model.TaskStatus;
import taskApp.repository.TaskRepository;
import taskApp.service.TaskService;

import java.util.NoSuchElementException;

@SpringBootTest(classes = TaskApplication.class)
@Transactional
public class TaskServiceIntegrationTest {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void createAndFindTaskTest(){
        String description = "Fix bug pd14";

        TaskRequest request = new TaskRequest();
        request.setDescription(description);

        TaskResponse created = taskService.create(request);
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getDescription(),created.getDescription());
        Assertions.assertEquals(TaskStatus.NEW,created.getStatus());
        Assertions.assertNotNull(created.getId());

        Long id = created.getId();
        TaskResponse found = taskService.read(id);
        Assertions.assertNotNull(found);
        Assertions.assertNotNull(description,found.getDescription());
        Assertions.assertEquals(TaskStatus.NEW,found.getStatus());
        Assertions.assertEquals(found.getId(),id);
    }

    @Test
    public void createAndUpdateTaskTest(){
        String description = "Fix bug pd14";

        TaskRequest request = new TaskRequest();
        request.setDescription(description);

        TaskResponse created = taskService.create(request);
        Assertions.assertNotNull(created);

        TaskRequest changeRequest = new TaskRequest();
        String changedDescription = "description changed";
        TaskStatus changedStatus = TaskStatus.IN_PROGRESS;
        changeRequest.setDescription(changedDescription);
        changeRequest.setStatus(changedStatus);
        TaskResponse updated = taskService.update(changeRequest,created.getId());

        Assertions.assertEquals(updated.getId(),created.getId());
        Assertions.assertEquals(changedStatus, updated.getStatus());
        Assertions.assertEquals(changedDescription, updated.getDescription());
        Assertions.assertEquals(created.getCreated(),updated.getCreated());

        Assertions.assertNotEquals(created.getModified(),updated.getModified());

    }

    @Test
    public void createAndDeleteTaskTest(){
        String description = "Fix bug pd14";

        TaskRequest request = new TaskRequest();
        request.setDescription(description);

        TaskResponse created = taskService.create(request);
        Assertions.assertNotNull(created);

        taskService.delete(created.getId());

        Assertions.assertThrows(TaskNotFoundException.class,() -> taskService.delete(created.getId()));

        Assertions.assertThrows(TaskNotFoundException.class,()->taskService.read(created.getId()));

    }

}
