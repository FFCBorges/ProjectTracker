package pt.ufp.edu.projecttracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ufp.edu.projecttracker.api.request.ClientDTO;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.service.ClientService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setup() {
        client = new Client();
        client.setUserID(22L);
        client.setName("Toni Caricas");
        client.setEmail("client@gmail.com");
        client.setPassword("passdotoni");


    }


    @Test
    void getClientByIDTest() throws Exception {

        when(clientService.extractClientByID(22L)).thenReturn(client);
        when(clientService.getClientByID(22L)).thenReturn(client);

        mockMvc.perform(get("/client/22")).andExpect(status().isOk());

        when(clientService.getClientByID(200L)).thenThrow(EntityNotFoundException404.class);

        mockMvc.perform(get("/client/200")).andExpect(status().isNotFound());

    }

    @Test
    void createClientTest() throws Exception {


        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setPassword(client.getPassword());

        Mockito.doNothing().when(clientService).createClient(client);
        mockMvc.perform(post("/client").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientDTO))).andExpect(status().isOk());

        Mockito.verify(clientService).createClient(any());
    }

    @Test
    void getAllClientsTest() throws Exception {


        when(clientService.getAllClients()).thenReturn(Lists.newArrayList(client));
        mockMvc.perform(get("/client")).andExpect(status().isOk());


    }


}