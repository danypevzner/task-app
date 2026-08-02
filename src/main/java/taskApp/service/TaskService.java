package taskApp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;
import taskApp.model.TaskStatus;

import java.time.LocalDateTime;

public interface TaskService {
    TaskResponse create(TaskRequest taskRequest);
    Page<TaskResponse> readAll(Pageable pageable, String search,TaskStatus status,Boolean overdue);
    TaskResponse read(Long id);
    TaskResponse update(TaskRequest taskRequest, Long id);
    void delete(Long id);
    TaskResponse updateStatus(Long id, TaskStatus status);

    TaskResponse updateDeadline(Long id, LocalDateTime deadLine);
}
