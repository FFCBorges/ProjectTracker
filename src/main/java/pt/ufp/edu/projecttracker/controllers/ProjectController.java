package pt.ufp.edu.projecttracker.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.ProjectDTO;
import pt.ufp.edu.projecttracker.service.ProjectService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ProjectController {


   private ProjectService projectService;

   @PostMapping("/project")
   public void createProject(@RequestBody @Valid ProjectDTO projectDTO){
       projectService.createProject(projectDTO);

   }

}
