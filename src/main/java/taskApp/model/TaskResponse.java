package taskApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data @AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String description;
    private TaskStatus status;
    private LocalDateTime created;
    private LocalDateTime modified;
}
