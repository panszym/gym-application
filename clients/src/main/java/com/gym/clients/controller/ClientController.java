package com.gym.clients.controller;

import com.gym.clients.model.Client;
import com.gym.clients.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Client> getClients() {
        logger.info("Display clients info.");
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Client getClient(@PathVariable Long id) {
        logger.info("Display client info.");
        return clientService.getClientById(id);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Client> getClientsByStatus(@RequestParam(required = false) Client.Status status) {
        logger.info("Display client by status.");
        return clientService.getClientsByStatus(status);
    }

    @GetMapping("/tickets")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Client> getClientsByTicket(@RequestParam(required = false) Client.Ticket ticket) {
        logger.info("Display client by ticket.");
        return clientService.getClientsByTicket(ticket);
    }

    @PostMapping("/emails")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<Client> getClientsByEmail(@RequestBody List<String> emails) {
        logger.info("Display client by email.");
        return clientService.getClientsByEmail(emails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public void deleteClient(@PathVariable Long id) {
        logger.info("Remove client from database.");
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Client putClient(@RequestBody @Valid Client client, @PathVariable Long id) {
        logger.info("Update client's data.");
        return clientService.putClient(client, id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public Client patchClient(@RequestBody Client client, @PathVariable Long id) {
        logger.info("Update client's data.");
        return clientService.patchClient(client, id);
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        clientService.toggleStatus(id);
        logger.info("Updated client's status.");
        return ResponseEntity.noContent().build();
    }
}
