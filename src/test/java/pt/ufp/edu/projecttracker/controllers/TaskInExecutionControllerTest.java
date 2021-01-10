package pt.ufp.edu.projecttracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ufp.edu.projecttracker.api.request.TaskInExecutionDTO;
import pt.ufp.edu.projecttracker.model.*;
import pt.ufp.edu.projecttracker.service.TaskInExecutionService;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskInExecutionController.class)
class TaskInExecutionControllerTest {


    @MockBean
    TaskInExecutionService taskInExecutionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Project project;
    private ProjectManager projectManager;
    private Employee employee;
    private TaskAsPlanned taskAsPlanned1;
    private TaskInExecution taskInExecution;

    @BeforeEach
    void setup() {

        project = new Project();
        projectManager = new ProjectManager();
        projectManager.setUserID(99L);
        employee = new Employee("Joao", "joao@email.com", "passdojoao", Role.JUNIOR_DEVELOPER);
        employee.setUserID(20L);
        project.setId(1L);
        project.setName("project");
        project.setProjectManager(projectManager);
        taskAsPlanned1 = new TaskAsPlanned("task 1", "do something", 10, project, Role.JUNIOR_ANALYST);
        taskAsPlanned1.setId(33L);
        taskAsPlanned1.setPlannedStartDate(LocalDate.of(2021, 1, 1));
        taskAsPlanned1.setPlannedDueDate(LocalDate.of(2021, 1, 31));
        taskInExecution = new TaskInExecution(taskAsPlanned1);
        taskInExecution.setId(taskAsPlanned1.getId());
        taskInExecution.setHoursUsed(0);
        taskInExecution.setExecutionRate(0D);


    }


    @Test
    void createTaskInExecutionTest() throws Exception {
        TaskInExecutionDTO dto = new TaskInExecutionDTO();
        dto.setTaskAsPlannedID(taskAsPlanned1.getId());
        dto.setExecutionRate(taskInExecution.getExecutionRate());
        dto.setHoursUsed(taskInExecution.getHoursUsed());
        when(taskInExecutionService.extractTaskAsPlannedByID(33L)).thenReturn(taskAsPlanned1);
        mockMvc.perform(MockMvcRequestBuilders.post("/taskinexecution").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk());

    }
}