package taskApp.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;
import taskApp.model.TaskStatus;
import taskApp.service.TaskService;
import taskApp.util.StringUtil;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(taskRequest));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAll(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String description,// Поиск по статусу
    @RequestParam(required = false) TaskStatus status,
    @RequestParam(required = false) Boolean overdue)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskResponse> tasks = taskService.readAll(pageable,description,status,overdue);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> get(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(taskService.read(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> put(@PathVariable(name = "id") Long id,@Valid @RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(taskService.update(taskRequest,id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> delete(@PathVariable(name = "id") Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                                     @RequestParam TaskStatus status){
        if (status==null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(taskService.updateStatus(id,status));
    }

    @PatchMapping(value = "/{id}/deadline")
    public ResponseEntity<TaskResponse> updateDeadline(@PathVariable Long id,@RequestBody LocalDateTime deadLine){
        return ResponseEntity.ok(taskService.updateDeadline(id,deadLine));
    }

}
