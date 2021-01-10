package pt.ufp.edu.projecttracker.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = ClientService.class)
class ClientServiceTest {

    @MockBean
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;

    private Client client;

    @BeforeEach
    void setup() {
        client = new Client("Zézé", "Josejose@gmail.com", "passdozeze");
        client.setUserID(1L);
    }

    @Test
    void getClientByIDTest() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        Client extractedClient = clientService.getClientByID(1L);
        Assertions.assertEquals(extractedClient.getName(), client.getName());
        try {
            clientService.getClientByID(200L);
            assert false;
        } catch (EntityNotFoundException404 e) {
            assert true;
        }
    }

    @Test
    void getAllClientsTest() {

        clientService.getAllClients();
        verify(clientRepository).findAll();
        Iterable<Client> clientList = clientRepository.findAll();
        Assertions.assertFalse(clientList.iterator().hasNext());
    }

    @Test
    void createClientTest() {
        clientService.createClient(client);
        verify(clientRepository).save(client);
    }


}