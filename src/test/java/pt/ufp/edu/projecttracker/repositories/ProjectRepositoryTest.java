package pt.ufp.edu.projecttracker.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pt.ufp.edu.projecttracker.model.Project;
import pt.ufp.edu.projecttracker.model.ProjectManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Test
    public void testProjectCreation(){
//
//        Project project = new Project();
//        ProjectManager manager = new ProjectManager();
//        project.setNome("Projecto Maravilha");
//
//        manager.setNome("Manel Maneger");
//        manager.setEmail("Manel@Oboss.com");
//        manager.setPassword("passwordDoManel")
//
//        project.setProjectManager(manager);

        ProjectManager manager = new ProjectManager("Manel Manager", "Manel@oBoss.com","passwordDoBoss");
        assertNotNull(manager);
        projectManagerRepository.save(manager);


        Project project = new Project("Projecto Maravilha",manager);

        Assertions.assertEquals(0, projectRepository.count());

        projectRepository.save(project);

        Assertions.assertEquals(1, projectRepository.count());


    }

}