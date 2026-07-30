package taskApp.service;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taskApp.model.Task;
import org.springframework.stereotype.Service;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;
import taskApp.model.TaskStatus;
import taskApp.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository repository;
    private static final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskResponse create(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        task.setModified(LocalDateTime.now());
        if(task.getStatus()==null){
            task.setStatus(TaskStatus.NEW);
        };
        logger.info("Task created");
        return taskMapper.toResponse(repository.save(task));
    }

    @Override
    public Page<TaskResponse> readAll(Pageable pageable) {
        logger.info("Reading task list");
        Page<Task> taskPage = repository.findAll(pageable);
        return taskPage.map(taskMapper::toResponse);
    }

    @Override
    public TaskResponse read(Long id) {
        logger.info("Reading task with id = {}", id);
        return taskMapper.toResponse(repository.findById(Long.valueOf(id)).orElseThrow(() -> new NoSuchElementException("Task not found by id = " + id)));
    }

    @Override
    public TaskResponse update(TaskRequest taskRequest, Long id) {
        Task existingTask = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + id));
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStatus(taskRequest.getStatus());
        existingTask.setModified(LocalDateTime.now());
        logger.info("Task with id = "+id+ "updated");
        return taskMapper.toResponse(repository.save(existingTask));

    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)){
            throw new NoSuchElementException("Task not found with id = "+id);
        }
        logger.info("Task with id = {} deleted", id);
        repository.deleteById(id);
    }
}
