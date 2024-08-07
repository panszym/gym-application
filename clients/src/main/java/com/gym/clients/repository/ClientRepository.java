package com.gym.clients.repository;

import com.gym.clients.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);

    List<Client> findAllByStatus(Client.Status status);

    List<Client> findAllByTicket(Client.Ticket ticket);

    List<Client> findAllByEmailIn(List<String> emails);
}
