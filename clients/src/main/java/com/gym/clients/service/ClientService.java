package com.gym.clients.service;

import com.gym.clients.auth.AuthenticationRequest;
import com.gym.clients.auth.AuthenticationResponse;
import com.gym.clients.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getClients();

    Client getClientById(Long id);

    List<Client> getClientsByStatus(Client.Status status);

    List<Client> getClientsByTicket(Client.Ticket ticket);

    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse registerClient(Client client);

    Client putClient(Client client, Long id);

    Client patchClient(Client client, Long id);

    void deleteClient(Long id);

    void toggleStatus(Long id);

    List<Client> getClientsByEmail(List<String> emails);


}
