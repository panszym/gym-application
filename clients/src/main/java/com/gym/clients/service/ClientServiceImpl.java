package com.gym.clients.service;

import com.gym.clients.model.Client;
import com.gym.clients.repository.ClientRepository;
import io.micrometer.common.util.StringUtils;
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
        Client client = clientRepository.findById(id).orElseThrow();
        return client;
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
                    dbClient.setFirstName(client.getFirstName());
                    dbClient.setLastName(client.getLastName());

                    return clientRepository.save(dbClient);
                }).orElseThrow();
    }

    @Override
    public Client patchClient(Client client, Long id) {
        return clientRepository.findById(id)
                .map(dbClient -> {
                    if (!StringUtils.isEmpty(client.getFirstName())) dbClient.setFirstName(client.getFirstName());
                    if (!StringUtils.isEmpty(client.getLastName())) dbClient.setLastName(client.getLastName());

                    return clientRepository.save(dbClient);
                }).orElseThrow();
    }

    private void checkClientEmail(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()))
            throw new IllegalArgumentException();
    }
}
