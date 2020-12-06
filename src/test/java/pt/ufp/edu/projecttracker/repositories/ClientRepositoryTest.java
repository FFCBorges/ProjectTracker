package pt.ufp.edu.projecttracker.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pt.ufp.edu.projecttracker.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

//carrega todos os objectos que estao no contentor do Spring
@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testClientCreation(){
        Client client = new Client();
        client.setEmail("client@test.com");
        client.setName("Joao Testarudo");
        client.setPassword("testword");

        assertEquals(0, clientRepository.count());

        clientRepository.save(client);

        assertEquals(1, clientRepository.count());



    }

}