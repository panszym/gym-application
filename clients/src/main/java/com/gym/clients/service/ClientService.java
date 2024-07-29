package com.gym.clients.service;

import com.gym.clients.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getClients();

    Client getClientById(Long id);

    List<Client> getClientsByStatus(Client.Status status);

    List<Client> getClientsByTicket(Client.Ticket ticket);

    Client addClient(Client client);

    Client putClient(Client client, Long id);

    Client patchClient(Client client, Long id);

    void deleteClient(Long id);

    void toggleStatus(Long id);

    List<Client> getClientsByEmail(List<String> emails);


}
