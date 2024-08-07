package com.gym.clients.service;

import com.gym.clients.exception.ClientException;
import com.gym.clients.exception.Error;
import com.gym.clients.model.Client;
import com.gym.clients.model.ClientDto;
import com.gym.clients.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        logger.info("getClient by id method.");
        ClientDto clientDto = new ClientDto(client.getFirstName(), client.getLastName(), client.getEmail(), client.getStatus(), client.getTicket());
        return clientDto;
    }

    @Override
    public List<Client> getClientsByStatus(Client.Status status) {
        logger.info("getClient by status method.");
        if (status != null) {
            return clientRepository.findAllByStatus(status);
        } else throw new ClientException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_STATUS);
    }

    ;

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
    public Client addClient(Client client) {
        logger.info("addClient method.");
        checkClientEmail(client);
        clientRepository.save(client);
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
    public Client patchClient(Client client, Long id) {
        logger.info("patchClient method.");
        return clientRepository.findById(id)
                .map(dbClient -> {
                    dbClient.updateClientPatch(client);
                    return clientRepository.save(dbClient);
                }).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
    }

    @Override
    public void toggleStatus(Long id) {
        logger.info("toggleStatus method.");
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        client.toggleStatus();
        clientRepository.save(client);
    }

    private void checkClientEmail(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()))
            throw new ClientException(Error.EMAIL_ALREADY_EXIST);
    }
}
