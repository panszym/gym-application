package com.gym.clients.service;

import com.gym.clients.auth.AuthenticationRequest;
import com.gym.clients.auth.AuthenticationResponse;
import com.gym.clients.config.JwtService;
import com.gym.clients.exception.ClientException;
import com.gym.clients.exception.Error;
import com.gym.clients.model.Client;
import com.gym.clients.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<Client> getClients() {
        logger.info("getClients method.");
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        logger.info("getClient by id method.");
        return client;
    }

    @Override
    public List<Client> getClientsByStatus(Client.Status status) {
        logger.info("getClient by status method.");
        if (status != null) {
            return clientRepository.findAllByStatus(status);
        } else throw new ClientException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_STATUS);
    }

    ;

    @Override
    public List<Client> getClientsByTicket(Client.Ticket ticket) {
        logger.info("getClient by ticket method.");
        if (ticket != null) {
            return clientRepository.findAllByTicket(ticket);
        } else throw new ClientException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_TICKET);
    }

    @Override
    public List<Client> getClientsByEmail(List<String> emails) {
        logger.info("getClients by email method.");
        return clientRepository.findAllByEmailIn(emails);
    }

    @Override
    public AuthenticationResponse registerClient(Client client) {
        logger.info("registerClient method.");
        checkClientEmail(client);
        client.activateClient();
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        var jwtToken = jwtService.generateToken(client);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var client = clientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ClientException(Error.THERE_IS_NOT_THAT_EMAIL_IN_THE_SYSTEM));
        var jwtToken = jwtService.generateToken(client);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public void deleteClient(Long id) {
        logger.info("deleteClient method.");
        if (!clientRepository.existsById(id)) throw new ClientException(Error.CLIENT_NOT_FOUND);
        clientRepository.deleteById(id);
    }

    @Override
    public Client putClient(Client client, Long id) {
        logger.info("putClient method.");
        return clientRepository.findById(id)
                .map(dbClient -> {
                    if (!dbClient.getEmail().equals(client.getEmail())) {
                        throw new ClientException(Error.DIFFERENT_EMAIL);
                    }
                    dbClient.updateClientPut(client);
                    return clientRepository.save(dbClient);
                }).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
    }

    @Override
    public Client patchClient(Client client, Long id) {
        logger.info("patchClient method.");
        return clientRepository.findById(id)
                .map(dbClient -> {
                    dbClient.updateClientPatch(client);
                    return clientRepository.save(dbClient);
                }).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
    }

    @Override
    public void toggleStatus(Long id) {
        logger.info("toggleStatus method.");
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientException(Error.CLIENT_NOT_FOUND));
        client.toggleStatus();
        clientRepository.save(client);
    }

    private void checkClientEmail(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()))
            throw new ClientException(Error.EMAIL_ALREADY_EXIST);
    }
}
