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
        ProjectManager manager = getDummyProjectManager1();

        assertEquals(0, projectManagerRepository.count());

        projectManagerRepository.save(manager);

        assertEquals(1, projectManagerRepository.count());

    }

    private ProjectManager getDummyProjectManager1() {
        ProjectManager manager = new ProjectManager();
        manager.setName("Manel Maneger");
        manager.setEmail("Manel@Oboss.com");
        manager.setPassword("passwordDoManel");
        return manager;
    }


    private ProjectManager getDummyProjectManager2() {
        ProjectManager manager = new ProjectManager();
        manager.setName("Joao Aristo");
        manager.setEmail("JA@gmail.com");
        manager.setPassword("passwordDoJoao");
        return manager;
    }

    @Test
    public void testGetProjectManagerByEmail(){

        ProjectManager manager1=getDummyProjectManager1();
        ProjectManager manager2=getDummyProjectManager2();
        projectManagerRepository.save(manager1);
        projectManagerRepository.save(manager2);

        assertEquals(2, projectManagerRepository.count());

        assertEquals(manager1, projectManagerRepository.getProjectManagerByEmail("Manel@Oboss.com"));
    }

    @Test
    public void testFindProjectManagerByEmail(){

        ProjectManager manager1=getDummyProjectManager1();
        ProjectManager manager2=getDummyProjectManager2();
        projectManagerRepository.save(manager1);
        projectManagerRepository.save(manager2);

        assertEquals(2, projectManagerRepository.count());

        assertEquals(manager1, projectManagerRepository.findProjectManagerByEmail("Manel@Oboss.com") );


    }











}