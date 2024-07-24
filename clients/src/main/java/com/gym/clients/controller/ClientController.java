package com.gym.clients.controller;

import com.gym.clients.model.Client;
import com.gym.clients.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients(){
        return clientService.getClients();
    }
    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id){
        return clientService.getClientById(id);
    }

    @PostMapping
    public Client addClient(@RequestBody @Valid Client client){
        return clientService.addClient(client);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
    }

    @PutMapping("/{id}")
    public Client putClient(@RequestBody @Valid Client client, @PathVariable Long id){
        return clientService.putClient(client, id);
    }

    @PatchMapping("/{id}")
    public Client patchClient(@RequestBody Client client, @PathVariable Long id){
        return clientService.patchClient(client, id);
    }
}
