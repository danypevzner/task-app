package taskApp.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import taskApp.model.Task;
import taskApp.model.TaskRequest;
import taskApp.model.TaskResponse;

import java.util.List;

@Mapper
public interface TaskMapper {
    public TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    public TaskResponse toResponse(Task task);
    public Task toTask(TaskRequest request);
    public List<TaskResponse> toResponseList(List<Task> tasks);

}
