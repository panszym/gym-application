package com.gym.clients.controller;

import com.gym.clients.auth.AuthenticationRequest;
import com.gym.clients.auth.AuthenticationResponse;
import com.gym.clients.model.Client;
import com.gym.clients.service.AuthServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    ResponseEntity<AuthenticationResponse> registerClient(@RequestBody @Valid Client client) {
        logger.info("Add client to database");
        var token = authService.registerClient(client);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticateClient(@RequestBody AuthenticationRequest request) throws Exception {
        logger.info("Authentication client.");
        var token = authService.authenticate(request);
        logger.info(token.toString());
        return ResponseEntity.ok(token);
    }
}

