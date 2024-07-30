package com.gym.clients.service;

import com.gym.clients.exception.ClientException;
import com.gym.clients.exception.Error;
import com.gym.clients.model.Client;
import com.gym.clients.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        return client;
    }

    @Override
    public List<Client> getClientsByStatus(Client.Status status) {
        if (status != null) {
            return clientRepository.findAllByStatus(status);
        } else throw new ClientException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_STATUS);
    }

    ;

    @Override
    public List<Client> getClientsByTicket(Client.Ticket ticket) {
        if (ticket != null) {
            return clientRepository.findAllByTicket(ticket);
        } else throw new ClientException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_TICKET);
    }

    @Override
    public List<Client> getClientsByEmail(List<String> emails) {
        return clientRepository.findAllByEmailIn(emails);
    }

    @Override
    public Client addClient(Client client) {
        checkClientEmail(client);
        clientRepository.save(client);
        return client;
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client putClient(Client client, Long id) {
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
        return clientRepository.findById(id)
                .map(dbClient -> {
                    dbClient.updateClientPatch(client);
                    return clientRepository.save(dbClient);
                }).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
    }

    @Override
    public void toggleStatus(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        client.toggleStatus();
        clientRepository.save(client);
    }

    private void checkClientEmail(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()))
            throw new ClientException(Error.EMAIL_ALREADY_EXIST);
    }
}
