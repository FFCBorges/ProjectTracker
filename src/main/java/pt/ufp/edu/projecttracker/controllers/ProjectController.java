package pt.ufp.edu.projecttracker.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pt.ufp.edu.projecttracker.api.request.ProjectDTO;
import pt.ufp.edu.projecttracker.api.response.ProjectDTOResponse;
import pt.ufp.edu.projecttracker.service.ProjectService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ProjectController {


   private ProjectService projectService;

   @PostMapping()
   public void createProject(@RequestBody @Valid ProjectDTO projectDTO){
       projectService.createProject(projectDTO);

   }


   @GetMapping("/{id}")
   public ProjectDTOResponse getProjectByID(@PathVariable("id") Long id){
       return projectService.getProjectByID(id);
   }

//   @GetMapping("/{id}/value")
//   public ProjectValueDTOResponse getProjectValueByID(@PathVariable("id") Long id){
//        return projectService.getProjectValueByID(id);
//
//    }


}
