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
import pt.ufp.edu.projecttracker.api.request.TaskAsPlannedDTO;
import pt.ufp.edu.projecttracker.api.request.TaskBindEmployeeDTO;
import pt.ufp.edu.projecttracker.model.*;
import pt.ufp.edu.projecttracker.service.TaskAsPlannedService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TaskAsPlannedController.class)
class TaskAsPlannedControllerTest {

    @MockBean
    TaskAsPlannedService taskAsPlannedService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Project project;
    private ProjectManager projectManager;
    private TaskAsPlanned taskAsPlanned1;
    private Client client;
    private Employee employee;

    @BeforeEach
    void setup() {

        project = new Project();
        projectManager = new ProjectManager();
        projectManager.setUserID(99L);
        client = new Client();
        client.setUserID(100L);
        client.setEmail("email@gmail.com");
        employee = new Employee("Joao", "joao@email.com", "passdojoao", Role.JUNIOR_DEVELOPER);
        employee.setUserID(20L);
        project.setId(1L);
        project.setName("project");
        project.setProjectManager(projectManager);
        project.setClient(client);
        taskAsPlanned1 = new TaskAsPlanned("task 1", "do something", 10, project, Role.JUNIOR_ANALYST);
        taskAsPlanned1.setId(33L);
        taskAsPlanned1.setPlannedStartDate(LocalDate.of(2021, 1, 1));
        taskAsPlanned1.setPlannedDueDate(LocalDate.of(2021, 1, 31));


    }


    @Test
    void createTaskAsPlannedTest() throws Exception {
        TaskAsPlannedDTO taskAsPlannedDTO = new TaskAsPlannedDTO(taskAsPlanned1.getTitle(), taskAsPlanned1.getDescription(),
                taskAsPlanned1.getEstimatedHours(), taskAsPlanned1.getEmployeeType(),
                project.getId(), employee.getUserID(), taskAsPlanned1.getPlannedStartDate(),
                taskAsPlanned1.getPlannedDueDate());
        mockMvc.perform(MockMvcRequestBuilders.post("/taskasplanned").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskAsPlannedDTO))).andExpect(status().isOk());

    }

    @Test
    void bindTaskToEmployeeTest() throws Exception {

        TaskBindEmployeeDTO dto = new TaskBindEmployeeDTO(employee.getUserID());
        mockMvc.perform(MockMvcRequestBuilders.patch("/taskasplanned/employee/33").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk());


    }


}