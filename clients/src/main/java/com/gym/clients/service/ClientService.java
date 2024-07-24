package com.gym.clients.service;

import com.gym.clients.model.Client;

import java.time.LocalDateTime;
import java.util.List;

public interface ClientService {

    List<Client> getClients();

    Client getClientById(Long id);

    Client addClient(Client client);

    Client putClient(Client client, Long id);

    Client patchClient(Client client, Long id);

    void deleteClient(Long id);


}
