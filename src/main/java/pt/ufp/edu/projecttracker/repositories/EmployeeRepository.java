package pt.ufp.edu.projecttracker.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ufp.edu.projecttracker.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long > {
}
