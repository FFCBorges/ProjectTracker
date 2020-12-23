package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.ProjectManagerDTO;
import pt.ufp.edu.projecttracker.api.response.ProjectManagerDTOResponse;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.repositories.ProjectManagerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectManagerService {

    private final ProjectManagerRepository projectManagerRepository;

    @Autowired
    public ProjectManagerService(ProjectManagerRepository projectManagerRepository){
        this.projectManagerRepository=projectManagerRepository;

    }

    @Transactional
    public void createProjectManager(ProjectManagerDTO projectManagerDTO){
        ProjectManager projectManager = new ProjectManager();
        projectManager.setName(projectManagerDTO.getName());
        projectManager.setEmail(projectManagerDTO.getEmail());
        projectManager.setPassword(projectManagerDTO.getPassword());
        projectManagerRepository.save(projectManager);
    }

    public ProjectManagerDTOResponse getProjectManagerByID(Long id){
       Optional<ProjectManager> optionalProjectManager=projectManagerRepository.findById(id);
       if(optionalProjectManager.isPresent()){
           ProjectManager projectManager = optionalProjectManager.get();
           ProjectManagerDTOResponse projectManagerDTOResponse = new ProjectManagerDTOResponse();
           projectManagerDTOResponse.setName(projectManager.getName());
           projectManagerDTOResponse.setEmail(projectManager.getEmail());
           projectManagerDTOResponse.setTotalNumberOfProjects(projectManager.totalNumberOfProjects());
           projectManagerDTOResponse.setNumberOfProjectsInExecution(projectManager.numberOfProjectsInExecution());
           return projectManagerDTOResponse;
       }

       return null;
    }


    public List<ProjectManagerDTOResponse> getAllProjectManagers(){
        Iterable<ProjectManager> projectManagers=projectManagerRepository.findAll();
        List<ProjectManagerDTOResponse> projectManagerDTOList = new ArrayList<>();

        for(ProjectManager p:projectManagers){
            projectManagerDTOList.add(new ProjectManagerDTOResponse(p.getName(),p.getEmail(),p.totalNumberOfProjects(),p.numberOfProjectsInExecution()));
        }

        return projectManagerDTOList;
    }
}
