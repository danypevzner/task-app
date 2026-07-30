package taskApp.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;
import taskApp.model.TaskStatus;
import taskApp.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest taskRequest) {
        logger.info("got post request");
        return ResponseEntity.ok(taskService.create(taskRequest));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAll(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size){
        logger.info("got get all tasks request");
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskResponse> tasks = taskService.readAll(pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> get(@PathVariable(name = "id") Long id){
        logger.info("got  get request for task {}",id);
        return ResponseEntity.ok(taskService.read(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> put(@PathVariable(name = "id") Long id,@Valid @RequestBody TaskRequest taskRequest){
        logger.info("got put request for task {}",id);
        return ResponseEntity.ok(taskService.update(taskRequest,id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> delete(@PathVariable(name = "id") Long id){
        logger.info("got delete request for task {}",id);
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id,
                                                     @RequestBody String statusString){
        TaskStatus status =TaskStatus.valueOf(statusString);
        return ResponseEntity.ok(taskService.updateStatus(id,status));
    }
}
