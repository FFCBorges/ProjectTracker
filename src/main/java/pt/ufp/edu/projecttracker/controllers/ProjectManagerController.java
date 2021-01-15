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
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.service.ProjectManagerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ProjectManagerController {

    private final ProjectManagerService projectManagerService;


    @Autowired
    public ProjectManagerController(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }


    @PostMapping()
    public void createProjectManager(@RequestBody ProjectManagerDTO projectManagerDTO) {
        ProjectManager manager = new ProjectManager();
        manager.setName(projectManagerDTO.getName());
        manager.setEmail(projectManagerDTO.getEmail());
        manager.setPassword(projectManagerDTO.getPassword());
        projectManagerService.createProjectManager(manager);
    }


    @GetMapping("/{id}")
    public ProjectManagerDTOResponse getProjectManagerByID(@PathVariable("id") Long id) {
        ProjectManager projectManager = projectManagerService.getProjectManagerByID(id);
        ProjectManagerDTOResponse projectManagerDTOResponse = new ProjectManagerDTOResponse();
        projectManagerDTOResponse.setName(projectManager.getName());
        projectManagerDTOResponse.setEmail(projectManager.getEmail());
        projectManagerDTOResponse.setTotalNumberOfProjects(projectManager.totalNumberOfProjects());
        projectManagerDTOResponse.setNumberOfProjectsInExecution(projectManager.numberOfProjectsInExecution());
        return projectManagerDTOResponse;


    }

    @GetMapping
    public Iterable<ProjectManagerDTOResponse> getAllProjectManagers() {
        Iterable<ProjectManager> projectManagers = projectManagerService.getAllProjectManagers();
        List<ProjectManagerDTOResponse> projectManagerDTOList = new ArrayList<>();
        for (ProjectManager p : projectManagers) {
            projectManagerDTOList.add(new ProjectManagerDTOResponse(p.getName(), p.getEmail(), p.totalNumberOfProjects(), p.numberOfProjectsInExecution()));
        }

        return projectManagerDTOList;

    }
}
