package pt.ufp.edu.projecttracker.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.ProjectDTO;
import pt.ufp.edu.projecttracker.api.response.ProjectDTOResponse;
import pt.ufp.edu.projecttracker.api.response.ProjectValueDTOResponse;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.exceptions.EntityNotFoundOnDB;
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
    public void createProject(ProjectDTO projectDTO){
        Project project = new Project();
        ProjectManager projectManager= extractProjectManagerByID(projectDTO);
        project.setProjectManager(projectManager);
        if(projectDTO.getClientID()!=null){
            project.setClient(extractClientByID(projectDTO));

        }
        project.setName(projectDTO.getName());
        project.setProjectDesc(projectDTO.getProjectDesc());

        projectRepository.save(project);

    }

    private ProjectManager extractProjectManagerByID(ProjectDTO projectDTO) {
        Optional<ProjectManager> optionalProjectManager = projectManagerRepository.findById(projectDTO.getProjectManagerID());
        if(optionalProjectManager.isPresent()){
            return optionalProjectManager.get();
        }
        throw new EntityNotFoundOnDB("Project Manager 404");
    }

    private Client extractClientByID(ProjectDTO projectDTO) {
        Optional<Client> optionalClient = clientRepository.findById(projectDTO.getClientID());
        if(optionalClient.isPresent()){
            return optionalClient.get();
        }
        throw new EntityNotFoundOnDB("Client 404");
    }


    public ProjectDTOResponse getProjectByID(Long id){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            ProjectDTOResponse projectDTO= new ProjectDTOResponse();
            projectDTO.setName(project.getName());
            projectDTO.setProjectManagerID(project.getProjectManager().getUserID());
            projectDTO.setClientID(project.getClient()==null? null: project.getClient().getUserID());
            projectDTO.setProjectDescription(project.getProjectDesc());
            projectDTO.setNumberOfTasks(project.numberOfTasks());
            projectDTO.setState(project.getProjectState());
            return projectDTO;
        }

        throw new EntityNotFoundException404("Projecto não Encontrado");

    }


//    public ProjectValueDTOResponse getProjectValueByID(Long id){
//        Optional<Project> optionalProject = projectRepository.findById(id);
//        if(optionalProject.isPresent()){
//            Project project = optionalProject.get();
//            ProjectValueDTOResponse projectDTO= new ProjectValueDTOResponse();
//            projectDTO.setProjectName(project.getName());
//            projectDTO.setPlannedBudget(project.getEstimatedProjectCost());
//            projectDTO.setExpenditure(project.getCurrentProjectCost());
//            projectDTO.setExecutionRate(project.getProjectExecutionRate());
//            projectDTO.setProjectState(project.getProjectState());
//            if(project.onTime()){
//                projectDTO.setOnTime("On Time");
//            }else{
//                projectDTO.setOnTime("Delayed");
//            }
//            return projectDTO;
//        }
//        return null;
//
//    }

    public ProjectValueDTOResponse getProjectValueByID(Long id){
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            ProjectValueDTOResponse response = new ProjectValueDTOResponse();
            response.setProjectName(project.getName());
            response.setPlannedBudget(project.getEstimatedProjectCost());
            return response;
        }

        throw new EntityNotFoundException404("Projecto não Encontrado");


    }
}
