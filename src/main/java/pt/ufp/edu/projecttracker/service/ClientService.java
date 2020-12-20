package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.ClientDTO;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;

import javax.transaction.Transactional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository=clientRepository;

    }

    @Transactional
    public void createClient(ClientDTO clientDTO){
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());
        clientRepository.save(client);

    }

}
