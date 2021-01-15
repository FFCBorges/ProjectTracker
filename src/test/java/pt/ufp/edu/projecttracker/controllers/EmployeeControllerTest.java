package pt.ufp.edu.projecttracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ufp.edu.projecttracker.api.request.EmployeeDTO;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.model.Role;
import pt.ufp.edu.projecttracker.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {


    @MockBean
    EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private Employee employeeJD;

    @BeforeEach
    void setup() {
        employeeJD = new Employee("Zézé", "Josejose@gmail.com", "passdozeze", Role.JUNIOR_DEVELOPER);
        employeeJD.setUserID(1L);

    }


    @Test
    void getEmployeeByIDTest() throws Exception {
        assertNotNull(employeeJD);
        when(employeeService.getEmployeeByID(1L)).thenReturn(employeeJD);
        mockMvc.perform(get("/employee/1")).andExpect(status().isOk());
        when(employeeService.getEmployeeByID(155L)).thenThrow(EntityNotFoundException404.class);
        mockMvc.perform(get("/employee/155")).andExpect(status().is(404));

    }


    @Test
    void getAllEmployees() throws Exception {
        assertNotNull(employeeJD);
        when(employeeService.getAllEmployees()).thenReturn(Lists.newArrayList(employeeJD));
        mockMvc.perform(get("/employee")).andExpect(status().isOk());

    }


    @Test
    void createEmployee() throws Exception {

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeJD.getName(), employeeJD.getEmail(), employeeJD.getPassword(), employeeJD.getRole());
        mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employeeDTO))).andExpect(status().isOk());

    }


}