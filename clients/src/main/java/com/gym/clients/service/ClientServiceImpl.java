package com.gym.clients.service;

import com.gym.clients.exception.ClientException;
import com.gym.clients.exception.Error;
import com.gym.clients.model.Client;
import com.gym.clients.model.ClientDto;
import com.gym.clients.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getClients() {
        logger.info("getClients method.");
        return clientRepository.findAll();
    }

    @Override
    public ClientDto getClientById(Principal principal) {
        Client client = clientRepository.findByEmail(principal.getName()).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        logger.info("getClient by id method.");
        if (!principal.getName().equals(client.getUsername())) {
            throw new ClientException(Error.THERE_IS_NOT_YOUR_ACCOUNT);
        }
        ClientDto clientDto = new ClientDto();
        clientDto.ClientToClientDtoAdapter(client);
        return clientDto;
    }

    @Override
    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        logger.info("getClient by id method.");
        return client;
    }

    @Override
    public ClientDto getClientByIdAdmin(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        logger.info("getClient by id method.");
        ClientDto clientDto = new ClientDto();
        clientDto.ClientToClientDtoAdapter(client);
        return clientDto;
    }

    @Override
    public Client getClientByIdFeign(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        logger.info("getClient by id method.");
        return client;
    }

    @Override
    public List<Client> getClientsByStatus(Client.Status status) {
        logger.info("getClient by status method.");
        if (status != null) {
            return clientRepository.findAllByStatus(status);
        } else throw new ClientException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_STATUS);
    }


    @Override
    public List<Client> getClientsByTicket(Client.Ticket ticket) {
        logger.info("getClient by ticket method.");
        if (ticket != null) {
            return clientRepository.findAllByTicket(ticket);
        } else throw new ClientException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_TICKET);
    }

    @Override
    public List<Client> getClientsByEmail(List<String> emails) {
        logger.info("getClients by email method.");
        return clientRepository.findAllByEmailIn(emails);
    }

    @Override
    public Client getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ClientException(Error.THERE_IS_NOT_THAT_EMAIL_IN_THE_SYSTEM));
        logger.info("getClientByEmail method.");
        return client;
    }

    @Override
    public void deleteClient(Long id) {
        logger.info("deleteClient method.");
        if (!clientRepository.existsById(id)) throw new ClientException(Error.CLIENT_NOT_FOUND);
        clientRepository.deleteById(id);
    }

    @Override
    public Client putClient(Client client, Long id) {
        logger.info("putClient method.");
        return clientRepository.findById(id)
                .map(dbClient -> {
                    if (!dbClient.getEmail().equals(client.getEmail())) {
                        throw new ClientException(Error.DIFFERENT_EMAIL);
                    }
                    dbClient.updateClientPut(client);
                    return clientRepository.save(dbClient);
                }).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
    }

    @Override
    public ClientDto patchClient(Client client, Long id) {
        logger.info("patchClient method.");
        return clientRepository.findById(id)
                .map(dbClient -> {
                    dbClient.updateClientPatch(client);
                    clientRepository.save(dbClient);
                    ClientDto clientDto = new ClientDto();
                    clientDto.ClientToClientDtoAdapter(dbClient);
                    return clientDto;
                }).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
    }

    @Override
    public void toggleStatus(Long id) {
        logger.info("toggleStatus method.");
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        client.toggleStatus();
        clientRepository.save(client);
    }


}
