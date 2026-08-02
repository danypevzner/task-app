package taskApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter @Setter
@Entity @Table(name = "tasks")
@AllArgsConstructor @NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "description",nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @CreationTimestamp
    private LocalDateTime created;
    private LocalDateTime modified;
    @Column(name = "deadline_date")
    private LocalDateTime deadline;
}
