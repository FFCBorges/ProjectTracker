package pt.ufp.edu.projecttracker.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.TaskAsPlannedDTO;
import pt.ufp.edu.projecttracker.api.request.TaskBindEmployeeDTO;
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
        taskAsPlannedService.createTaskAsPlanned(taskAsPlannedDTO);
    }

    @PatchMapping("/employee/{id}")
    public void bindTaskToEmployee(@PathVariable("id") Long id, @RequestBody TaskBindEmployeeDTO employeeIDDTO){
           taskAsPlannedService.bindTaskToEmployee(id, employeeIDDTO);

    }


}
