package pt.ufp.edu.projecttracker.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectManagerRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectManagerRepository projectManagerRepository;
    private final ClientRepository clientRepository;




    @Transactional
    public void createProject(Project project){
        projectRepository.save(project);
    }

    public ProjectManager extractProjectManagerByID(Long id) {
        Optional<ProjectManager> optionalProjectManager = projectManagerRepository.findById(id);
        if(optionalProjectManager.isPresent()){
            return optionalProjectManager.get();
        }
        throw new BadRequestException400("The Project Manager assigned to this Project does not exist");
    }

    public Client extractClientByID(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if(optionalClient.isPresent()){
            return optionalClient.get();
        }
        throw new BadRequestException400("The Client assigned to this Project does not exist");
    }


    public Project getProjectByID(Long id){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            return optionalProject.get();
        }
        throw new EntityNotFoundException404("Project Not Found");
    }

    public Project getProjectValueByID(Long id){
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(optionalProject.isPresent()){
            return optionalProject.get();
        }

        throw new EntityNotFoundException404("No Project found with such ID");
    }

    public Project getProjectTimeByID(Long id){
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(optionalProject.isPresent()){
           return optionalProject.get();

        }

        throw new EntityNotFoundException404("No Project found with such ID");
    }

    public Iterable<Project> getAllProjects(){
        return projectRepository.findAll();
    }

}
