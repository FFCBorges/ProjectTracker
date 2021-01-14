package pt.ufp.edu.projecttracker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.exceptions.EntityNotFoundOnDB;
import pt.ufp.edu.projecttracker.model.Employee;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.Role;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;
import pt.ufp.edu.projecttracker.repositories.EmployeeRepository;
import pt.ufp.edu.projecttracker.repositories.ProjectRepository;
import pt.ufp.edu.projecttracker.repositories.TaskAsPlannedRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TaskAsPlannedService.class)
class TaskAsPlannedServiceTest {

    @MockBean
    TaskAsPlannedRepository taskAsPlannedRepository;
    @MockBean
    ProjectRepository projectRepository;
    @MockBean
    EmployeeRepository employeeRepository;


    @Autowired
    TaskAsPlannedService taskAsPlannedService;

    private TaskAsPlanned taskAsPlanned;
    private Project project;
    private Employee employee;

    @BeforeEach
    void setup() {
        project = new Project();
        project.setId(1L);
        taskAsPlanned = new TaskAsPlanned("task 1", "do something", 10, project, Role.JUNIOR_ANALYST);
        taskAsPlanned.setId(33L);
        taskAsPlanned.setPlannedStartDate(LocalDate.of(2021, 1, 1));
        taskAsPlanned.setPlannedDueDate(LocalDate.of(2021, 1, 31));
        employee = new Employee();
        employee.setUserID(22L);
        employee.setRole(Role.JUNIOR_ANALYST);
    }

    @Test
    void createTaskAsPlannedTest() {
        taskAsPlannedService.createTaskAsPlanned(taskAsPlanned);
        verify(taskAsPlannedRepository).save(taskAsPlanned);

    }

    @Test
    void extractProjectByIDTest() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Project extractedProject = taskAsPlannedService.extractProjectByID(1L);
        Assertions.assertEquals(project, extractedProject);
        try {
            taskAsPlannedService.extractProjectByID(200L);
            assert false;
        } catch (EntityNotFoundOnDB e) {
            assert true;
        }
    }

    @Test
    void extractEmployeeByIDTest() {
        when(employeeRepository.findById(22L)).thenReturn(Optional.of(employee));
        Employee extractedEmployee = taskAsPlannedService.extractEmployeeByID(22L);
        Assertions.assertEquals(employee, extractedEmployee);
        try {
            taskAsPlannedService.extractEmployeeByID(200L);
            assert false;
        } catch (EntityNotFoundException404 e) {
            assert true;
        }
    }

    @Test
    void bindTaskToEmployeeTest() {
        when(taskAsPlannedRepository.findById(33L)).thenReturn(Optional.of(taskAsPlanned));
        when(employeeRepository.findById(22L)).thenReturn(Optional.of(employee));
        taskAsPlannedService.bindTaskToEmployee(33L, employee);
        Assertions.assertEquals(22L, taskAsPlanned.getEmployee().getUserID());
        employee.setRole(Role.SENIOR_ANALYST);

        try {
            taskAsPlannedService.bindTaskToEmployee(33L, employee);
            assert false;

        } catch (BadRequestException400 e) {
            assert true;
        }

        try {
            taskAsPlannedService.bindTaskToEmployee(999L, employee);
            assert false;

        } catch (EntityNotFoundException404 e) {
            assert true;
        }

    }

    @Test
    void createAndBindTaskToProjectTest() {
        when(employeeRepository.findById(22L)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskAsPlannedRepository.findById(33L)).thenReturn(Optional.of(taskAsPlanned));
        taskAsPlanned.setProject(null);
        taskAsPlanned.setEmployee(null);
        taskAsPlannedService.createAndBindTaskToProject(taskAsPlanned, 1L, 22L);
        taskAsPlanned = taskAsPlannedRepository.findById(33L).get();
        Assertions.assertEquals(22L, taskAsPlanned.getEmployee().getUserID());
        Assertions.assertEquals(1L, taskAsPlanned.getProject().getId());
        employee.setRole(Role.SENIOR_ANALYST);
        try {
            taskAsPlannedService.createAndBindTaskToProject(taskAsPlanned, 1L, 22L);
            assert false;
        } catch (BadRequestException400 e) {
            assert true;
        }
    }

}