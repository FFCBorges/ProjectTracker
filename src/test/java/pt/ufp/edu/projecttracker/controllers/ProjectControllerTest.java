package pt.ufp.edu.projecttracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ufp.edu.projecttracker.api.request.ProjectDTO;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.model.Role;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;
import pt.ufp.edu.projecttracker.service.ProjectService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private Project project;
    private ProjectManager projectManager;
    private Client client;
    //private TaskAsPlanned taskAsPlanned1;
    //private TaskAsPlanned taskAsPlanned2;


    @BeforeEach
    void setup(){
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
        TaskAsPlanned taskAsPlanned1 = new TaskAsPlanned("task 1", "do something", 10, project, Role.JUNIOR_ANALYST);
        taskAsPlanned1.setPlannedStartDate(LocalDate.of(2021, 1, 1));
        taskAsPlanned1.setPlannedDueDate(LocalDate.of(2021, 1, 31));
        TaskAsPlanned taskAsPlanned2 = new TaskAsPlanned("task 2", "do something else", 15, project, Role.SENIOR_DEVELOPER);
        taskAsPlanned2.setPlannedStartDate(LocalDate.of(2021, 1, 10));
        taskAsPlanned2.setPlannedDueDate(LocalDate.of(2021, 1, 20));


    }



    @Test
    void getProjectByIDTest() throws Exception {
        assertNotNull(project);
        when(projectService.getProjectByID(1L)).thenReturn(project);

        mockMvc.perform(get("/project/1")).andExpect(status().isOk());

    }

    @Test
    void createProjectTest() throws Exception{
        //projectService = Mockito.mock(ProjectService.class);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(project.getName());
        projectDTO.setClientID(client.getUserID());
        projectDTO.setProjectManagerID(projectManager.getUserID());
        projectDTO.setProjectDesc("descricao generica");

        when(projectService.extractProjectManagerByID(99L)).thenReturn(projectManager);
        when(projectService.extractClientByID(100L)).thenReturn(client);

        Mockito.doNothing().when(projectService).createProject(project);
        mockMvc.perform(MockMvcRequestBuilders.post("/project").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO))).andExpect(status().isOk());

        Mockito.verify(projectService).createProject(any());
    }

    @Test
    void getAllProjectsTest() throws Exception{
        assertNotNull(project);
        when(projectService.getAllProjects()).thenReturn(Lists.newArrayList(project));
        mockMvc.perform(get("/project")).andExpect(status().isOk());

    }

    @Test
    void getProjectValueByID() throws Exception{
        assertNotNull(project);
        when(projectService.getProjectValueByID(1L)).thenReturn(project);
        when(projectService.getProjectValueByID(155L)).thenThrow(EntityNotFoundException404.class);
        mockMvc.perform(get("/project/1/value")).andExpect(status().isOk());
        mockMvc.perform(get("/project/155/value")).andExpect(status().is(404));


    }

    @Test
    void getProjectTimeByID()throws Exception{
        assertNotNull(project);
        when(projectService.getProjectTimeByID(1L)).thenReturn(project);
        when(projectService.getProjectTimeByID(100L)).thenThrow(EntityNotFoundException404.class);
        mockMvc.perform(get("/project/1/time")).andExpect(status().isOk());
        mockMvc.perform(get("/project/100/time")).andExpect(status().is(404));

    }

}