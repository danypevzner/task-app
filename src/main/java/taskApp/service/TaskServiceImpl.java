package taskApp.service;

import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taskApp.exception.TaskNotFoundException;
import taskApp.model.*;
import org.springframework.stereotype.Service;
import taskApp.repository.TaskRepository;
import taskApp.specification.Specifications;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private static final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    private final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskResponse create(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        task.setModified(LocalDateTime.now());
        task.setDeadline(taskRequest.getDeadline());
        if(task.getStatus()==null){
            task.setStatus(TaskStatus.NEW);
        }
        logger.info("Creating task");
        return taskMapper.toResponse(repository.save(task));
    }

    @Override
    public Page<TaskResponse> readAll(Pageable pageable, String search,TaskStatus status,Boolean overdue) {
        Page<Task> taskPage;
        logger.info("Reading task list");
        taskPage = repository.findAll(Specifications.descriptionContains(search,status,overdue),pageable);
        return taskPage.map(taskMapper::toResponse);
    }

    @Override
    public TaskResponse read(Long id) {
        logger.info("Reading task with id = {}", id);
        return taskMapper.toResponse(getTask(id));
    }

    @Override
    public TaskResponse update(TaskRequest taskRequest, Long id) {
        Task existingTask = getTask(id);
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStatus(taskRequest.getStatus());
        existingTask.setModified(LocalDateTime.now());
        existingTask.setDeadline(taskRequest.getDeadline());
        logger.info("Updating task with id = {}", id);
        return taskMapper.toResponse(repository.save(existingTask));

    }

    @Override
    public TaskResponse updateStatus(Long id, TaskStatus status){
        Task existingTask = getTask(id);
        existingTask.setStatus(status);
        logger.info("Updating task status with id = {}",id);
        return taskMapper.toResponse(repository.save(existingTask));
    }

    @Override
    public TaskResponse updateDeadline(Long id,LocalDateTime deadLine){
        Task existingTask = getTask(id);
        existingTask.setDeadline(deadLine);
        logger.info("Changed deadline on task{}",id);
        return taskMapper.toResponse(repository.save(existingTask));
    }

    @Override
    public void delete(Long id) {
        getTask(id);
        logger.info("Deleting task with id = {}", id);
        repository.deleteById(id);
    }

    private Task getTask(Long id) throws NoSuchElementException{
        return repository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
    }
}
