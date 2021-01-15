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
import pt.ufp.edu.projecttracker.api.request.ProjectManagerDTO;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.service.ProjectManagerService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectManagerController.class)
class ProjectManagerControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectManagerService projectManagerService;

    private ProjectManager projectManager;

    @BeforeEach
    void setup(){
        projectManager=new ProjectManager();
        projectManager.setUserID(22L);


    }



    @Test
    void getProjectManagerByIDTest() throws Exception {
        assertNotNull(projectManager);
        when(projectManagerService.getProjectManagerByID(20L)).thenReturn(projectManager);

        mockMvc.perform(get("/manager/20")).andExpect(status().isOk());

        when(projectManagerService.getProjectManagerByID(200L)).thenThrow(EntityNotFoundException404.class);

        mockMvc.perform(get("/manager/200")).andExpect(status().isNotFound());

    }

    @Test
    void createProjectManagerTest() throws Exception{
        //projectService = Mockito.mock(ProjectService.class);

        ProjectManagerDTO projectManagerDTO = new ProjectManagerDTO();
        projectManagerDTO.setName(projectManager.getName());
        projectManagerDTO.setEmail(projectManager.getEmail());
        projectManagerDTO.setPassword(projectManager.getPassword());

        Mockito.doNothing().when(projectManagerService).createProjectManager(projectManager);
        mockMvc.perform(post("/manager").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(projectManagerDTO))).andExpect(status().isOk());

        Mockito.verify(projectManagerService).createProjectManager(any());
    }

    @Test
    void getAllProjectManagersTest() throws Exception{


        when(projectManagerService.getAllProjectManagers()).thenReturn(Lists.newArrayList(projectManager));
        mockMvc.perform(get("/manager")).andExpect(status().isOk());



    }


}