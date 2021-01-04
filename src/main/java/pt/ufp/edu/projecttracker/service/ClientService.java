package pt.ufp.edu.projecttracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ufp.edu.projecttracker.api.request.ClientDTO;
import pt.ufp.edu.projecttracker.api.response.ClientDTOResponse;
import pt.ufp.edu.projecttracker.controllers.advices.exceptions.EntityNotFoundException404;
import pt.ufp.edu.projecttracker.model.Client;
import pt.ufp.edu.projecttracker.repositories.ClientRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public void createClient(ClientDTO clientDTO){
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPassword(clientDTO.getPassword());
        clientRepository.save(client);

    }

    public ClientDTOResponse getClientByID(Long id){
        Client client=extractClientByID(id);
        ClientDTOResponse response = new ClientDTOResponse();
        response.setName(client.getName());
        response.setEmail(client.getEmail());
        response.setNumberOfProjects(client.getProjects().size());
        response.setNumberOfOngoingProjects(client.getNumberOfOngoingProjects());
        return response;

    }

    public Client extractClientByID(Long id){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) return client.get();
        throw new EntityNotFoundException404("Client With That ID Was Not Found");

    }

    public List<ClientDTOResponse> getAllClients(){
        Iterable<Client> clients=clientRepository.findAll();
        List<ClientDTOResponse> clientDTOList = new ArrayList<>();
        for(Client c:clients){
            clientDTOList.add(new ClientDTOResponse(c.getName(),c.getEmail(),c.getProjects().size(),c.getNumberOfOngoingProjects()));
        }

        return clientDTOList;
    }




}
