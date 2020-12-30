package pt.ufp.edu.projecttracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.TaskAsPlannedDTO;
import pt.ufp.edu.projecttracker.api.request.TaskBindEmployeeDTO;
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
    public void createTaskAsPlanned(TaskAsPlannedDTO taskAsPlannedDTO){
        TaskAsPlanned taskAsPlanned = new TaskAsPlanned();
        taskAsPlanned.setTitle(taskAsPlannedDTO.getTitle());
        taskAsPlanned.setDescription(taskAsPlannedDTO.getDescription());
        taskAsPlanned.setEstimatedHours(taskAsPlannedDTO.getEstimatedHours());
        taskAsPlanned.setEmployeeType(taskAsPlannedDTO.getEmployeeType());
        taskAsPlanned.setProject(extractProjectByID(taskAsPlannedDTO));
        if(taskAsPlannedDTO.getEmployeeID()!=null){
            taskAsPlanned.setEmployee(
                    employeeRepository
                    .findById(taskAsPlannedDTO.getEmployeeID())
                    .orElse(null));
        }


        taskAsPlanned.setPlannedStartDate(taskAsPlannedDTO.getPlannedStartDate());
        taskAsPlanned.setPlannedDueDate(taskAsPlannedDTO.getPlannedDueDate());
        taskAsPlannedRepository.save(taskAsPlanned);


    }


    private Project extractProjectByID(TaskAsPlannedDTO taskAsPlannedDTO) {
        Optional<Project> optionalProject = projectRepository.findById(taskAsPlannedDTO.getProjectID());
        if(optionalProject.isPresent()){
            return optionalProject.get();
        }
        throw new EntityNotFoundOnDB("Project 404");
    }

    //Todo verificações
    @Transactional
    public void bindTaskToEmployee(Long id, TaskBindEmployeeDTO employeeIDDTO) {
        Optional<TaskAsPlanned> optionalTaskAsPlanned = taskAsPlannedRepository.findById(id);
        Long employeeID=employeeIDDTO.getEmployeeID();
        Optional<Employee> optionalEmployee=employeeRepository.findById(employeeID);
        if(optionalEmployee.isPresent() && optionalTaskAsPlanned.isPresent()){
            Employee employee=optionalEmployee.get();
            TaskAsPlanned taskAsPlanned=optionalTaskAsPlanned.get();
            taskAsPlanned.setEmployee(employee);
            employee.addTask(taskAsPlanned);
        }



    }
}
