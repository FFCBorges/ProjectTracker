package pt.ufp.edu.projecttracker.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {
    private Client client;
    private Project project;
    private ProjectManager projectManager;


    @BeforeEach
    void setup() {
    client=new Client("Antonio", "antonio@gmail.com","passdoantonio");
        projectManager = new ProjectManager();

    }

    @Test
    void getClientData() {
        assertEquals(client.getName(),"Antonio");
        assertEquals(client.getEmail(),"antonio@gmail.com");
        assertEquals(client.getPassword(),"passdoantonio");
    }

    @Test
    void getNumberOfOngoingProjects() {
        assertEquals(client.getNumberOfOngoingProjects(),0);
        project = new Project("Project Client Test Client", projectManager, client);
        assertEquals(client.getNumberOfOngoingProjects(), 1);

    }

    @Test
    void setClientData() {
        Client client1 = new Client();
        client1.setUserID(100L);
        client1.setName("Eduardo");
//        client1.setEmail("novoemail@gmail.com");
        Assertions.assertThatObject(client1);
        assertEquals(100L, client1.getUserID());
        assertEquals("Eduardo", client1.getName());
    }

}