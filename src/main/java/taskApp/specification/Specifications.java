package taskApp.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import taskApp.model.Task;
import taskApp.model.TaskStatus;

import java.time.LocalDateTime;

public  class Specifications{
    public static Specification<Task> descriptionContains(String search, TaskStatus status,Boolean overdue) {
        Specification<Task> spec =  Specification.where((root, query, cb) -> cb.conjunction());

        if (search != null && !search.isBlank()){
            String pattern = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        }

        if (status!=null){
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status)
            );
        }
        if (overdue != null) {
            LocalDateTime now = LocalDateTime.now();

            if (overdue) {
                spec = spec.and((root, query, cb) -> {
                    Predicate hasDeadline = cb.isNotNull(root.get("deadline"));
                    Predicate isLate = cb.lessThan(root.get("deadline"), now);
                    return cb.and(hasDeadline, isLate);
                });
            }
        }
        return spec;
    }

}