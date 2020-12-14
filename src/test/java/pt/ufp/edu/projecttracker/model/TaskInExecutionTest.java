package pt.ufp.edu.projecttracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskInExecutionTest {

    private Project project;
    private TaskAsPlanned task1;
    private TaskAsPlanned task2;
    private TaskAsPlanned task3;

    @BeforeEach
    public void setup(){
        project=new Project();

        task1=new TaskAsPlanned(10, Role.JUNIOR_DEVELOPER,project,
                LocalDate.of(2020, 11, 1),LocalDate.of(2020, 11, 20));
        task1.getTaskInExecution().setHoursUsed(10);
        task1.getTaskInExecution().setExecutionRate(1d);
        project.addTask(task1);

        task2=new TaskAsPlanned(5, Role.SENIOR_DEVELOPER,project,
                LocalDate.of(2020, 11, 15),LocalDate.of(2020, 11, 25));
        task2.getTaskInExecution().setHoursUsed(10);
        task2.getTaskInExecution().setExecutionRate(0.5d);
        project.addTask(task2);


        task3 =new TaskAsPlanned(15, Role.JUNIOR_ANALYST,project,
                LocalDate.of(2019, 12, 15),LocalDate.of(2020, 12, 10));
        project.addTask(task3);


    }



    @Test
    void onTime() {
        //test before the starting date
        assertEquals(true, task1.getTaskInExecution().onTime(LocalDate.of(2020, 10, 1)));
        //test between the starting date and due date, the task is completely done so it is expect true (the task is on time);
        assertEquals(true, task1.getTaskInExecution().onTime(LocalDate.of(2020, 11, 10)));
        //test after the due date, the task is completely done so it is expect true (the task is on time);
        assertEquals(true, task1.getTaskInExecution().onTime(LocalDate.of(2020, 12, 10)));
        //true
        assertEquals(true, task2.getTaskInExecution().onTime(LocalDate.of(2020, 11, 10)));
        //true
        assertEquals(true, task2.getTaskInExecution().onTime(LocalDate.of(2020, 11, 20)));
        //false
        assertEquals(false, task2.getTaskInExecution().onTime(LocalDate.of(2020, 11, 21)));
        //false
        assertEquals(false, task2.getTaskInExecution().onTime(LocalDate.of(2020, 11, 30)));
    }


}