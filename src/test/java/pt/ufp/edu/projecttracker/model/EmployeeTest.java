package pt.ufp.edu.projecttracker.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeTest {

    private Employee employee;
    private TaskAsPlanned task1;
    private Project project;

    @BeforeEach
    public void setup() {

        //Starting date: 2019/12/15   Due date:2020/12/10 Not Finished
        project = new Project();

        task1 = new TaskAsPlanned(10, Role.JUNIOR_DEVELOPER, project,
                LocalDate.of(2020, 11, 1), LocalDate.of(2020, 11, 20));
        task1.getTaskInExecution().setHoursUsed(10);
        task1.getTaskInExecution().setExecutionRate(1d);
        task1.getTaskInExecution().setFinishedBy(LocalDate.of(2020, 11, 21));
        project.addTask(task1);

        employee = new Employee("Joao", "Joao@mail.com", "passdojoao", Role.JUNIOR_DEVELOPER);


    }

    @Test
    public void getRoleRateTest() {
        assertNotNull(employee);
        Assertions.assertEquals(10, employee.getRoleRate());
        employee.setRole(Role.JUNIOR_ANALYST);
        Assertions.assertEquals(20, employee.getRoleRate());
        employee.setRole(Role.SENIOR_DEVELOPER);
        Assertions.assertEquals(40, employee.getRoleRate());
        employee.setRole(Role.SENIOR_ANALYST);
        Assertions.assertEquals(80, employee.getRoleRate());
    }

    @Test
    public void getTaskTest() {
        employee.setRole(Role.JUNIOR_DEVELOPER);
        employee.addTask(task1);
        task1.setId(99L);
        Assertions.assertEquals(task1, employee.getTask(99L));
        employee.setTasks(null);
        employee.setTasks(new ArrayList<>());
        Assertions.assertNull(employee.getTask(99L));
    }

    @Test
    public void getNumberOfOngoingTasks() {
        Assertions.assertEquals(0, employee.getNumberOfOngoingTasks());

    }


}