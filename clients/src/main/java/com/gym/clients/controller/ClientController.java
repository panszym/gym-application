package com.gym.clients.controller;

import com.gym.clients.model.Client;
import com.gym.clients.model.ClientDto;
import com.gym.clients.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/clients")
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Client> getClients() {
        logger.info("Display clients info.");
        return clientService.getClients();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ClientDto getClient(@PathVariable Long id) {
        logger.info("Display client info.");
        return clientService.getClientById(id);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ClientDto getClient(Principal principal) {
        logger.info("Display client info.");
        return clientService.getClientById(principal);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Client> getClientsByStatus(@RequestParam(required = false) Client.Status status) {
        logger.info("Display client by status.");
        return clientService.getClientsByStatus(status);
    }

    @GetMapping("/tickets")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Client> getClientsByTicket(@RequestParam(required = false) Client.Ticket ticket) {
        logger.info("Display client by ticket.");
        return clientService.getClientsByTicket(ticket);
    }

    @PostMapping("/emails")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Client> getClientsByEmail(@RequestBody List<String> emails) {
        logger.info("Display client by email.");
        return clientService.getClientsByEmail(emails);
    }

    @PostMapping("/email")
    public Client getClientByEmail(@RequestParam String email) {
        logger.info("Display client by email.");
        return clientService.getClientByEmail(email);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteClient(@PathVariable Long id) {
        logger.info("Remove client from database.");
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    public Client putClient(@RequestBody @Valid Client client, @PathVariable Long id) {
        logger.info("Update client's data.");
        return clientService.putClient(client, id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    public ClientDto patchClient(@RequestBody Client client, @PathVariable Long id) {
        logger.info("Update client's data.");
        return clientService.patchClient(client, id);
    }

    @PatchMapping("/status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        clientService.toggleStatus(id);
        logger.info("Updated client's status.");
        return ResponseEntity.noContent().build();
    }
}
