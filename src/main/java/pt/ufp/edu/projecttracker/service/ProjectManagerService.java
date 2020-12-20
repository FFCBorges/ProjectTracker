package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.ProjectManagerDTO;
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

    public ProjectManagerDTO getProjectManagerByID(Long id){
       Optional<ProjectManager> optionalProjectManager=projectManagerRepository.findById(id);
       if(optionalProjectManager.isPresent()){
           ProjectManager projectManager = optionalProjectManager.get();
           ProjectManagerDTO projectManagerDTO = new ProjectManagerDTO();
           projectManagerDTO.setName(projectManager.getName());
           projectManagerDTO.setEmail(projectManager.getEmail());
           return projectManagerDTO;
       }

       return null;
    }

    // criar novo dto extendendo o existente com os atributos desejados (novo dto no response)

    public List<ProjectManagerDTO> getAllProjectManagers(){
        Iterable<ProjectManager> projectManagers=projectManagerRepository.findAll();
        List<ProjectManagerDTO> projectManagerDTOList = new ArrayList<>();

        for(ProjectManager p:projectManagers){
            projectManagerDTOList.add(new ProjectManagerDTO(p.getName(),p.getEmail(),null));
        }

        return projectManagerDTOList;
    }
}
