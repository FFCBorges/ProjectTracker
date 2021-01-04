package pt.ufp.edu.projecttracker.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.ProjectDTO;
import pt.ufp.edu.projecttracker.api.response.ProjectDTOResponse;
import pt.ufp.edu.projecttracker.api.response.ProjectTimeDTOResponse;
import pt.ufp.edu.projecttracker.api.response.ProjectValueDTOResponse;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectManagerRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
        //fazer verificaçao de projecto id
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
        throw new BadRequestException400("The Project Manager assigned to this Project does not exist");
    }

    private Client extractClientByID(ProjectDTO projectDTO) {
        Optional<Client> optionalClient = clientRepository.findById(projectDTO.getClientID());
        if(optionalClient.isPresent()){
            return optionalClient.get();
        }
        throw new BadRequestException400("The Client assigned to this Project does not exist");
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

        throw new EntityNotFoundException404("Project Not Found");

    }




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

    public ProjectTimeDTOResponse getProjectTimeByID(Long id){
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(optionalProject.isPresent()){
            Project project = optionalProject.get();
            ProjectTimeDTOResponse response = new ProjectTimeDTOResponse();
            response.setProjectName(project.getName());
            response.setStartDate(project.getStartingDate());
            response.setDueDate(project.getDueDate());
            response.setManHours(project.getManHours());
            return response;
        }

        throw new EntityNotFoundException404("No Project found with such ID");
    }

    public List<ProjectDTOResponse> getAllProjects(){
        Iterable<Project> projects=projectRepository.findAll();
        List<ProjectDTOResponse> projectDTOList = new ArrayList<>();

        for(Project p:projects){
            if(p.getClient()!=null) {
                projectDTOList.add(new ProjectDTOResponse(p.getName(), p.getProjectManager().getUserID(), p.getClient().getUserID(), p.getProjectDesc(), p.getProjectState(), p.getPlannedTasks().size()));
            }else{
                projectDTOList.add(new ProjectDTOResponse(p.getName(), p.getProjectManager().getUserID(), null, p.getProjectDesc(), p.getProjectState(), p.getPlannedTasks().size()));
            }
        }

        return projectDTOList;
    }

}
