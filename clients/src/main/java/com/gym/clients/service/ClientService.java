package com.gym.clients.service;

import com.gym.clients.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getClients();

    Client addClient(Client client);

    void deleteClient(Long id);
}
