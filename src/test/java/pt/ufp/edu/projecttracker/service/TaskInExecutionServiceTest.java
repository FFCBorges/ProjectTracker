package pt.ufp.edu.projecttracker.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ufp.edu.projecttracker.exceptions.EntityNotFoundOnDB;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.Role;
import pt.ufp.edu.projecttracker.model.TaskAsPlanned;
import pt.ufp.edu.projecttracker.model.TaskInExecution;
import pt.ufp.edu.projecttracker.repositories.TaskAsPlannedRepository;
import pt.ufp.edu.projecttracker.repositories.TaskInExecutionRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TaskInExecutionService.class)
public class TaskInExecutionServiceTest {

    @MockBean
    TaskAsPlannedRepository taskAsPlannedRepository;
    @MockBean
    TaskInExecutionRepository taskInExecutionRepository;

    @Autowired
    TaskInExecutionService taskInExecutionService;

    private Project project;
    private TaskAsPlanned taskAsPlanned;
    private TaskInExecution taskInExecution;


    @BeforeEach
    void setup() {
        project = new Project();
        project.setId(1L);
        taskAsPlanned = new TaskAsPlanned("task 1", "do something", 10, project, Role.JUNIOR_ANALYST);
        taskAsPlanned.setId(33L);
        taskAsPlanned.setPlannedStartDate(LocalDate.of(2021, 1, 1));
        taskAsPlanned.setPlannedDueDate(LocalDate.of(2021, 1, 31));
        taskInExecution = taskAsPlanned.getTaskInExecution();
    }


    @Test
    void extractPlannedTaskByIDTest() {
        assertNotNull(taskAsPlanned);
        when(taskAsPlannedRepository.findById(33L)).thenReturn(Optional.of(taskAsPlanned));
        TaskAsPlanned extractedTaskAsPlanned = taskInExecutionService.extractTaskAsPlannedByID(33L);
        Assertions.assertEquals(taskAsPlanned, extractedTaskAsPlanned);
        try {
            taskInExecutionService.extractTaskAsPlannedByID(44L);
            assert false;
        } catch (EntityNotFoundOnDB e) {
            assert true;
        }
    }

    @Test
    void createTaskInExecutionTest() {
        taskInExecutionService.createTaskInExecution(taskInExecution);
        verify(taskInExecutionRepository).save(taskInExecution);

    }


}
