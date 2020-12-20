package pt.ufp.edu.projecttracker.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.TaskAsPlannedDTO;
import pt.ufp.edu.projecttracker.service.TaskAsPlannedService;

import javax.validation.Valid;

//considerar utilizar o @RequiredArgsConstructor (not nulls e finals)
@RestController
@AllArgsConstructor
public class TaskAsPlannedController {

    private final TaskAsPlannedService taskAsPlannedService;

    @PostMapping("/taskasplanned")
    public void createTaskAsPlanned(@RequestBody @Valid TaskAsPlannedDTO taskAsPlannedDTO){
        taskAsPlannedService.createTaskAsPlanned(taskAsPlannedDTO);
    }
}
