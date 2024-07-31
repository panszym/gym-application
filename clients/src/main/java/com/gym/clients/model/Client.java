package com.gym.clients.model;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "idGenerator", initialValue = 1, allocationSize = 1)
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
    private Long id;

    @NotBlank(message = "FirstName must not be blank.")
    private String firstName;
    @NotBlank(message = "LastName must not be blank.")
    private String lastName;
    @NotBlank(message = "Email must not be blank.")
    @Email
    private String email;

    @NotNull(message = "Status must not be null.")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Status must not be null.")
    @Enumerated(EnumType.STRING)
    private Ticket ticket;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Ticket getTicket() {
        return ticket;
    }

    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void updateClientPut(Client client) {
        setFirstName(client.getFirstName());
        setLastName(client.getLastName());
        setEmail(client.getEmail());
        setStatus(client.getStatus());
        setTicket(client.getTicket());
    }

    public void updateClientPatch(Client client) {
        if (!StringUtils.isEmpty(client.getFirstName())) setFirstName(client.getFirstName());
        if (!StringUtils.isEmpty(client.getLastName())) setLastName(client.getLastName());
        if (client.getStatus() != null) setStatus(client.getStatus());
        if (client.getTicket() != null) setTicket(client.getTicket());

    }

    public void toggleStatus(){
        if (Status.ACTIVE.equals(this.getStatus())){
            setStatus(Status.INACTIVE);
        }else setStatus(Status.ACTIVE);
    }


    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Ticket {
        NORMAL,
        PREMIUM,
        MASTER
    }
}

