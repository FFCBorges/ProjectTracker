package pt.ufp.edu.projecttracker.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;

// estende de component, passa a ser um bean,
@Repository
public interface TaskRepository extends CrudRepository<TaskAsPlanned, Long > {
}
