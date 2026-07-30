package taskApp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {
    @NotBlank(message = " task description required")
    @Size(min = 2,max = 200,message = "task description length should be in [2,200]")
    private String description;
    private TaskStatus status;
}
