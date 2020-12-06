package pt.ufp.edu.projecttracker.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pt.ufp.edu.projecttracker.model.ProjectManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ProjectManagerRepositoryTest {

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Test
    public void testProjectManagerCreation() {
        ProjectManager manager = new ProjectManager();
        manager.setName("Manel Maneger");
        manager.setEmail("Manel@Oboss.com");
        manager.setPassword("passwordDoManel");

        assertEquals(0, projectManagerRepository.count());

        projectManagerRepository.save(manager);

        assertEquals(1, projectManagerRepository.count());

    }



}