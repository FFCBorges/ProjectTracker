package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.repositories.ProjectManagerRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProjectManagerService {

    private final ProjectManagerRepository projectManagerRepository;

    @Autowired
    public ProjectManagerService(ProjectManagerRepository projectManagerRepository){
        this.projectManagerRepository=projectManagerRepository;

    }

    @Transactional
    public void createProjectManager(ProjectManager projectManager){
        projectManagerRepository.save(projectManager);
    }

    public ProjectManager getProjectManagerByID(Long id) {
        Optional<ProjectManager> optionalProjectManager = projectManagerRepository.findById(id);
        if (optionalProjectManager.isPresent()) {
            return optionalProjectManager.get();
        }
        throw new BadRequestException400("No such Manager with such ID exists");

    }

    public Iterable<ProjectManager> getAllProjectManagers(){
        return projectManagerRepository.findAll();
    }
}
