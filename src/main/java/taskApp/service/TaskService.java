package taskApp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taskApp.model.Task;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse create(TaskRequest taskRequest);
    Page<TaskResponse> readAll(Pageable pageable);
    TaskResponse read(Long id);
    TaskResponse update(TaskRequest taskRequest, Long id);
    void delete(Long id);
}
