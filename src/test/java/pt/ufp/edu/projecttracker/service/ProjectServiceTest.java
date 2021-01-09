package pt.ufp.edu.projecttracker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.*;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectManagerRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProjectService.class)
class ProjectServiceTest {



    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    ProjectManagerRepository projectManagerRepository;

    @MockBean
    ClientRepository clientRepository;

    @Autowired
    ProjectService projectService;



    private Project project;
    private ProjectManager projectManager;
    private Client client;
    private TaskAsPlanned taskAsPlanned1;
    private TaskAsPlanned taskAsPlanned2;



    @BeforeEach
    void setup() {

       // projectService = new ProjectService(projectRepository, projectManagerRepository, clientRepository);

        project=new Project();
        projectManager = new ProjectManager();
        projectManager.setUserID(99L);
        client= new Client();
        client.setUserID(100L);
        client.setEmail("email@gmail.com");
        project.setId(1L);
        project.setName("project");
        project.setProjectManager(projectManager);
        project.setClient(client);
        taskAsPlanned1 = new TaskAsPlanned("task 1", "do something", 10, project, Role.JUNIOR_ANALYST);
        taskAsPlanned1.setPlannedStartDate(LocalDate.of(2021, 1, 1));
        taskAsPlanned1.setPlannedDueDate(LocalDate.of(2021, 1, 31));
        taskAsPlanned2 = new TaskAsPlanned("task 2", "do something else", 15, project, Role.SENIOR_DEVELOPER);
        taskAsPlanned2.setPlannedStartDate(LocalDate.of(2021, 1, 10));
        taskAsPlanned2.setPlannedDueDate(LocalDate.of(2021, 1, 20));

    }


    @Test
    void getProjectByIDTest(){

        when(projectRepository.findById(22L)).thenReturn(Optional.of(project));
        Project projectExtracted = projectService.getProjectByID(22L);
        Assertions.assertEquals(projectExtracted.getName(), project.getName() );
        //when(projectRepository.findById(200L)).thenReturn(Optional.empty());
        try {
            projectService.getProjectByID(200L);
            assert false;
        }
        catch(EntityNotFoundException404 e){
            assert true;
        }
    }

    @Test
    void extractProjectManagerByIDTest(){

        when(projectManagerRepository.findById(22L)).thenReturn(Optional.of(projectManager));
        ProjectManager manager= projectService.extractProjectManagerByID(22L);
        Assertions.assertEquals(projectManager, manager );
        try {
            projectService.extractProjectManagerByID(200L);
            assert false;
        }
        catch(BadRequestException400 e){
            assert true;
        }

    }

    @Test
    void extractClientByIDTest(){

        when(clientRepository.findById(100L)).thenReturn(Optional.of(client));
        Client extractedClient= projectService.extractClientByID(100L);
        Assertions.assertEquals(client, extractedClient );
        try {
            projectService.extractClientByID(200L);
            assert false;
        }
        catch(BadRequestException400 e){
            assert true;
        }

    }

    @Test
    void getProjectValueByIDTest(){

        when(projectRepository.findById(22L)).thenReturn(Optional.of(project));
        Project projectExtracted = projectService.getProjectValueByID(22L);
        Assertions.assertEquals(projectExtracted.getName(), project.getName() );

        try {
            projectService.getProjectValueByID(200L);
            assert false;
        }
        catch(EntityNotFoundException404 e){
            assert true;
        }


    }

    @Test
    void getProjectTimeByIDTest(){

        when(projectRepository.findById(22L)).thenReturn(Optional.of(project));
        Project projectExtracted = projectService.getProjectTimeByID(22L);
        Assertions.assertEquals(projectExtracted.getName(), project.getName() );

        try {
            projectService.getProjectTimeByID(200L);
            assert false;
        }
        catch(EntityNotFoundException404 e){
            assert true;
        }

    }

    @Test
    void createProjectTest(){

        projectService.createProject(project);
        verify(projectRepository).save(project);

    }

    @Test
    void getAllProjectsTest(){

        projectService.getAllProjects();
        verify(projectRepository).findAll();
        Iterable<Project> projectList = projectRepository.findAll();
        Assertions.assertFalse( projectList.iterator().hasNext());

    }
























}