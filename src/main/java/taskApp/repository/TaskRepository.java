package taskApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taskApp.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
