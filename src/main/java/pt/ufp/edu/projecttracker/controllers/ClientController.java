package pt.ufp.edu.projecttracker.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.ufp.edu.projecttracker.api.request.ClientDTO;
import pt.ufp.edu.projecttracker.service.ClientService;

import javax.validation.Valid;

@RestController
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService=clientService;
    }

    @PostMapping("/client")
    public void createClient(@RequestBody @Valid ClientDTO clientDTO){
        clientService.createClient(clientDTO);
    }

//    @RequestMapping(
//            value = "/ex/foos",
//            method = GET,
//            consumes = "application/json",
//            produces = "application/json")
//
//    @ResponseBody
//    public String getFoosAsJsonFromBrowser() {
//        return "Get some Foos with Header Old";
//    }


}
