package pt.ufp.edu.projecttracker.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ufp.edu.projecttracker.model.ProjectManager;

@Repository
public interface ProjectManagerRepository extends CrudRepository<ProjectManager, Long > {

ProjectManager getProjectManagerByEmail(String email);

@Query(value = "SELECT pm from ProjectManager as pm WHERE pm.email=:email")
ProjectManager findProjectManagerByEmail(String email);




}
