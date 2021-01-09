package pt.ufp.edu.projecttracker.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ufp.edu.projecttracker.api.request.ClientDTO;
import pt.ufp.edu.projecttracker.api.response.ClientDTOResponse;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.service.ClientService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    @PostMapping("")
    public void createClient(@RequestBody @Valid ClientDTO clientDTO){
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());
        clientService.createClient(client);
    }

    @GetMapping("/{id}")
    public ClientDTOResponse getClientByID(@PathVariable("id") Long id){
        Client client= clientService.getClientByID(id);
        ClientDTOResponse clientDTO=new ClientDTOResponse();
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setNumberOfOngoingProjects(client.getNumberOfOngoingProjects());
        clientDTO.setNumberOfProjects(client.getProjects().size());
        return clientDTO;

    }


    @GetMapping
    public List<ClientDTOResponse> getAllClients(){
        List <Client> clients=clientService.getAllClients();
        List<ClientDTOResponse> clientDTOList = new ArrayList<>();
        for(Client c:clients){
            clientDTOList.add(new ClientDTOResponse(c.getName(),c.getEmail(),c.getProjects().size(),c.getNumberOfOngoingProjects()));
        }
        return clientDTOList;
    }



}
