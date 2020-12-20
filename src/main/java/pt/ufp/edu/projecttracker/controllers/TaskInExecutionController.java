package pt.ufp.edu.projecttracker.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.TaskInExecutionDTO;
import pt.ufp.edu.projecttracker.service.TaskInExecutionService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class TaskInExecutionController {

    private final TaskInExecutionService taskInExecutionService;

    @PostMapping("/taskinexecution")
    public void createTaskInExecution(@RequestBody @Valid TaskInExecutionDTO taskInExecutionDTO){
        taskInExecutionService.createTaskInExecution(taskInExecutionDTO);
    }


}
