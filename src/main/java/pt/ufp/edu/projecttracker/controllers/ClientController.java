package pt.ufp.edu.projecttracker.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ufp.edu.projecttracker.api.request.ClientDTO;
import pt.ufp.edu.projecttracker.api.response.ClientDTOResponse;
import pt.ufp.edu.projecttracker.service.ClientService;

import javax.validation.Valid;
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
        clientService.createClient(clientDTO);
    }

    @GetMapping("/{id}")
    public ClientDTOResponse getClientByID(@PathVariable("id") Long id){
        return clientService.getClientByID(id);

    }


    @GetMapping
    public List<ClientDTOResponse> getAllClients(){
        return clientService.getAllClients();
    }



}
