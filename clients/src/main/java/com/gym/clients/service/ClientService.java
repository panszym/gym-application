package com.gym.clients.service;

import com.gym.clients.model.Client;
import com.gym.clients.model.ClientDto;

import java.security.Principal;
import java.util.List;

public interface ClientService {

    List<Client> getClients();

    ClientDto getClientById( Principal principal);

    ClientDto getClientByIdAdmin(Long id);

    Client getClientById(Long id);

    List<Client> getClientsByStatus(Client.Status status);

    List<Client> getClientsByTicket(Client.Ticket ticket);

    Client putClient(Client client, Long id);

    ClientDto patchClient(Client client, Long id);

    void deleteClient(Long id);

    void toggleStatus(Long id);

    List<Client> getClientsByEmail(List<String> emails);

    Client getClientByEmail(String email);


}
