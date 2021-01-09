package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository=clientRepository;

    }

    @Transactional
    public void createClient(Client client){
        clientRepository.save(client);

    }

    public Client getClientByID(Long id){
        return extractClientByID(id);

    }

    public Client extractClientByID(Long id){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) return client.get();
        throw new EntityNotFoundException404("Client With That ID Was Not Found");

    }

    public List<Client> getAllClients(){
        return (List<Client>) clientRepository.findAll();

    }




}
