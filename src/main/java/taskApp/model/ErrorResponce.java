package taskApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data @AllArgsConstructor
public class ErrorResponce {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
}
