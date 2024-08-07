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

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

    private void checkClientEmail(Client client) {
        if (clientRepository.existsByEmail(client.getEmail()))
            throw new ClientException(Error.EMAIL_ALREADY_EXIST);
    }
}
