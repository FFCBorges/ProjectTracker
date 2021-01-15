package pt.ufp.edu.projecttracker.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.ProjectDTO;
import pt.ufp.edu.projecttracker.api.response.ProjectDTOResponse;
import pt.ufp.edu.projecttracker.api.response.ProjectTimeDTOResponse;
import pt.ufp.edu.projecttracker.api.response.ProjectValueDTOResponse;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.service.ProjectService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ProjectController {




   private ProjectService projectService;

   @PostMapping()
   public void createProject(@RequestBody @Valid ProjectDTO projectDTO){

       ProjectManager projectManager= projectService.extractProjectManagerByID(projectDTO.getProjectManagerID());
       Project project = new Project();
       project.setProjectManager(projectManager);
       project.setName(projectDTO.getName());
       project.setProjectDesc(projectDTO.getProjectDesc());
       if(projectDTO.getClientID()!=null){
           project.setClient(projectService.extractClientByID(projectDTO.getClientID()));

       }
       projectService.createProject(project);

   }


   @GetMapping("/{id}")
   public ProjectDTOResponse getProjectByID(@PathVariable("id") Long id){
       Project project = projectService.getProjectByID(id);
       ProjectDTOResponse projectDTO= new ProjectDTOResponse();
       projectDTO.setName(project.getName());
       projectDTO.setProjectManagerID(project.getProjectManager().getUserID());
       projectDTO.setClientID(project.getClient()==null? null: project.getClient().getUserID());
       projectDTO.setProjectDescription(project.getProjectDesc());
       projectDTO.setNumberOfTasks(project.numberOfTasks());
       projectDTO.setState(project.getProjectState());
       return projectDTO;
   }

    @GetMapping("/{id}/value")
    public ProjectValueDTOResponse getProjectValueByID(@PathVariable("id") Long id){
        Project project = projectService.getProjectValueByID(id);
        ProjectValueDTOResponse response = new ProjectValueDTOResponse();
        response.setProjectName(project.getName());
        response.setPlannedBudget(project.getEstimatedProjectCost());
        return response;

    }

    @GetMapping("/{id}/time")
    public ProjectTimeDTOResponse getProjectTimeByID(@PathVariable("id") Long id){
        Project project= projectService.getProjectTimeByID(id);
        ProjectTimeDTOResponse response = new ProjectTimeDTOResponse();
        response.setProjectName(project.getName());
        response.setStartDate(project.getStartingDate());
        response.setDueDate(project.getDueDate());
        response.setManHours(project.getManHours());
        return response;

    }

    @GetMapping
    public List<ProjectDTOResponse> getAllProjects(){
       Iterable<Project> projectList= projectService.getAllProjects();
       ArrayList<ProjectDTOResponse> projectDTOList = new ArrayList<>();
        for(Project p:projectList){
            if(p.getClient()!=null) {
                projectDTOList.add(new ProjectDTOResponse(p.getName(), p.getProjectManager().getUserID(), p.getClient().getUserID(), p.getProjectDesc(), p.getProjectState(), p.getPlannedTasks().size()));
            }else{
                projectDTOList.add(new ProjectDTOResponse(p.getName(), p.getProjectManager().getUserID(), null, p.getProjectDesc(), p.getProjectState(), p.getPlannedTasks().size()));
            }
        }

        return projectDTOList;
    }

}
