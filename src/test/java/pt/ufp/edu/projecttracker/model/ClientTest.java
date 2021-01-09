package pt.ufp.edu.projecttracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {
    public Client client;

    @BeforeEach
    public void setup(){
    client=new Client("Antonio", "antonio@gmail.com","passdoantonio");

    }

    @Test
    public void getClientData(){
        assertEquals(client.getName(),"Antonio");
        assertEquals(client.getEmail(),"antonio@gmail.com");
        assertEquals(client.getPassword(),"passdoantonio");
    }

    @Test
    public void getNumberOfOngoingProjects(){
        assertEquals(client.getNumberOfOngoingProjects(),0);
    }

}