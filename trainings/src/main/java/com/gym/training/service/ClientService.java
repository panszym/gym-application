package com.gym.training.service;

import com.gym.training.model.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientService {

    @GetMapping("/clients")
    List<ClientDto> getClients();

    @GetMapping("/clients/{clientId}")
    ClientDto getClientById(@PathVariable Long clientId);

    @PostMapping("/clients/emails")
    List<ClientDto> getClientsByEmail(@RequestBody List<String> emails);
}
