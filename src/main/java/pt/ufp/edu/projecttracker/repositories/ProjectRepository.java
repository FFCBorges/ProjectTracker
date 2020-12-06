package pt.ufp.edu.projecttracker.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ufp.edu.projecttracker.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long > {


}
