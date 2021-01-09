package pt.ufp.edu.projecttracker.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pt.ufp.edu.projecttracker.api.request.TaskAsPlannedDTO;
import pt.ufp.edu.projecttracker.api.request.TaskBindEmployeeDTO;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;
import pt.ufp.edu.projecttracker.service.TaskAsPlannedService;

import javax.validation.Valid;

//considerar utilizar o @RequiredArgsConstructor (not nulls e finals)
@RestController
@AllArgsConstructor
@RequestMapping("/taskasplanned")
public class TaskAsPlannedController {

    private final TaskAsPlannedService taskAsPlannedService;

    @PostMapping
    public void createTaskAsPlanned(@RequestBody @Valid TaskAsPlannedDTO taskAsPlannedDTO){
        TaskAsPlanned task= new TaskAsPlanned();
        task.setEstimatedHours(taskAsPlannedDTO.getEstimatedHours());
        task.setEmployeeType(taskAsPlannedDTO.getEmployeeType());
        task.setPlannedStartDate(taskAsPlannedDTO.getPlannedStartDate());
        task.setPlannedDueDate(taskAsPlannedDTO.getPlannedDueDate());
        task.setProject(taskAsPlannedService.extractProjectByID(taskAsPlannedDTO.getProjectID()));
        task.setTitle(taskAsPlannedDTO.getTitle());
        task.setDescription(taskAsPlannedDTO.getDescription());
        if(taskAsPlannedDTO.getEmployeeID()!=null){
            task.setEmployee(taskAsPlannedService.extractEmployeeByID(taskAsPlannedDTO.getEmployeeID()));
        }

        taskAsPlannedService.createTaskAsPlanned(task);
    }

    @PatchMapping("/employee/{id}")
    public void bindTaskToEmployee(@PathVariable("id") Long id, @RequestBody TaskBindEmployeeDTO employeeIDDTO){
        Employee employee=new Employee();
        employee.setUserID(employeeIDDTO.getEmployeeID());
           taskAsPlannedService.bindTaskToEmployee(id, employee);

    }


}
