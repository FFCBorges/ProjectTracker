package pt.ufp.edu.projecttracker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.BadRequestException400;
import pt.ufp.edu.projecttracker.model.ProjectManager;
import pt.ufp.edu.projecttracker.repositories.ProjectManagerRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProjectManagerService.class)
class ProjectManagerServiceTest {

    @MockBean
    ProjectManagerRepository projectManagerRepository;

    @Autowired
    ProjectManagerService projectManagerService;

    private ProjectManager projectManager;

    @BeforeEach
    void setup() {
        projectManager = new ProjectManager();
        projectManager.setUserID(1L);
    }

    @Test
    void getProjectManagerByIDTest() {
        when(projectManagerRepository.findById(1L)).thenReturn(Optional.of(projectManager));

        ProjectManager extractedManager = projectManagerService.getProjectManagerByID(1L);
        Assertions.assertEquals(projectManager.getUserID(), extractedManager.getUserID());

        try {
            projectManagerService.getProjectManagerByID(200L);
            assert false;
        } catch (BadRequestException400 e) {
            assert true;
        }

    }

    @Test
    void getAllProjectManagersTest() {

        projectManagerService.getAllProjectManagers();
        verify(projectManagerRepository).findAll();
        Iterable<ProjectManager> projectManagerList = projectManagerRepository.findAll();
        Assertions.assertFalse(projectManagerList.iterator().hasNext());

    }

    @Test
    void createProjectManagerTest() {
        projectManagerService.createProjectManager(projectManager);
        verify(projectManagerRepository).save(projectManager);
    }


}