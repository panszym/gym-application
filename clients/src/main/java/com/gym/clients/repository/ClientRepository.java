package com.gym.clients.repository;

import com.gym.clients.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);

    List<Client> findAllByStatus(Client.Status status);

    List<Client> findAllByTicket(Client.Ticket ticket);
}
