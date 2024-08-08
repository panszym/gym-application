package com.gym.clients.model;

import lombok.Data;

@Data
public class ClientDto {

    private String firstName;
    private String lastName;
    private String email;
    private Client.Status status;
    private Client.Ticket ticket;


    public ClientDto ClientToClientDtoAdapter(Client client){
        setFirstName(client.getFirstName());
        setLastName(client.getLastName());
        setEmail(client.getEmail());
        setStatus(client.getStatus());
        setTicket(client.getTicket());
        return this;
    }
}


