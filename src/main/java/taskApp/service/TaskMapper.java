package taskApp.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import taskApp.model.Task;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;

import java.util.List;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    TaskResponse toResponse(Task task);
    Task toTask(TaskRequest request);
    List<TaskResponse> toResponseList(List<Task> tasks);
}
