package com.gym.clients.model;

public class ClientDto {

    private String firstName;
    private String lastName;
    private String email;
    private Client.Status status;
    private Client.Ticket ticket;

    public ClientDto(String firstName, String lastName, String email, Client.Status status, Client.Ticket ticket) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.ticket = ticket;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Client.Status getStatus() {
        return status;
    }

    public void setStatus(Client.Status status) {
        this.status = status;
    }

    public Client.Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Client.Ticket ticket) {
        this.ticket = ticket;
    }

}


