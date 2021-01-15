package pt.ufp.edu.projecttracker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.model.Role;
import pt.ufp.edu.projecttracker.repositories.EmployeeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = EmployeeService.class)
class EmployeeServiceTest {

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    private Employee employeeJD;


    @BeforeEach
    void setup() {
        employeeJD = new Employee("Zézé", "Josejose@gmail.com", "passdozeze", Role.JUNIOR_DEVELOPER);
        employeeJD.setUserID(1L);
    }

    @Test
    void getEmployeeByIDTest() {
        assertNotNull(employeeJD);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employeeJD));
        Employee extractedEmployee = employeeService.getEmployeeByID(1L);
        Assertions.assertEquals(extractedEmployee.getName(), employeeJD.getName());
        try {
            employeeService.getEmployeeByID(200L);
            assert false;
        } catch (EntityNotFoundException404 e) {
            assert true;
        }
    }

    @Test
    void getAllEmployeesTest() {

        employeeService.getAllEmployees();
        verify(employeeRepository).findAll();
        Iterable<Employee> employeeList = employeeRepository.findAll();
        Assertions.assertFalse(employeeList.iterator().hasNext());
    }

    @Test
    void createEmployeeTest() {
        employeeService.createEmployee(employeeJD);
        verify(employeeRepository).save(employeeJD);
    }


}