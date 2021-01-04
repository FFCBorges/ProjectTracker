package pt.ufp.edu.projecttracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.ProjectManagerDTO;
import pt.ufp.edu.projecttracker.api.response.ProjectManagerDTOResponse;
import pt.ufp.edu.projecttracker.service.ProjectManagerService;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ProjectManagerController {

    private final ProjectManagerService projectManagerService;


    @Autowired
    public ProjectManagerController(ProjectManagerService projectManagerService){
        this.projectManagerService=projectManagerService;
    }




    @PostMapping()
    public void createProjectManager(@RequestBody ProjectManagerDTO projectManagerDTO){
        projectManagerService.createProjectManager(projectManagerDTO);
    }


    @GetMapping("/{id}")
    public ProjectManagerDTOResponse getProjectManagerByID(@PathVariable("id") Long id){
        return projectManagerService.getProjectManagerByID(id);

    }

    @GetMapping
    public List<ProjectManagerDTOResponse> getAllProjectManagers(){
        return projectManagerService.getAllProjectManagers();
    }
}
