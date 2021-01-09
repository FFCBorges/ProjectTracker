package pt.ufp.edu.projecttracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.exceptions.EntityNotFoundOnDB;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;
import pt.ufp.edu.projecttracker.repositories.EmployeeRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectRepository;
import pt.ufp.edu.projecttracker.repositories.TaskAsPlannedRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskAsPlannedService {

    private final TaskAsPlannedRepository taskAsPlannedRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void createTaskAsPlanned(TaskAsPlanned taskAsPlanned){
//        if(taskAsPlannedDTO.getEmployeeID()!=null){
//            taskAsPlanned.setEmployee(
//                    employeeRepository
//                    .findById(taskAsPlannedDTO.getEmployeeID())
//                    .orElse(null));
//        }
        taskAsPlannedRepository.save(taskAsPlanned);
    }


    public Project extractProjectByID(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            return optionalProject.get();
        }
        throw new EntityNotFoundOnDB("Project 404");
    }


    @Transactional
    public void bindTaskToEmployee (Long id, Employee e){
        Optional<TaskAsPlanned> optionalTaskAsPlanned = taskAsPlannedRepository.findById(id);
        Long employeeID=e.getUserID();
        Optional<Employee> optionalEmployee=employeeRepository.findById(employeeID);
        if(optionalEmployee.isPresent() && optionalTaskAsPlanned.isPresent()){
            Employee employee=optionalEmployee.get();
            TaskAsPlanned taskAsPlanned=optionalTaskAsPlanned.get();
            if(!employee.getRole().equals(taskAsPlanned.getEmployeeType())) throw new BadRequestException400("Employ role does not match Task's needs");
            taskAsPlanned.setEmployee(employee);
            employee.addTask(taskAsPlanned);
        } else{
            throw new EntityNotFoundException404("Either the Employee, the Task or both do not exist");
        }

    }

    public Employee extractEmployeeByID(Long id){
       Optional<Employee> optionalEmployee = employeeRepository.findById(id);
       if(optionalEmployee.isPresent()){
        return optionalEmployee.get();
       }
        throw new EntityNotFoundException404("The Employee corresponding to the id supplied was not found");
    }
}
