package com.gym.clients.service;

import com.gym.clients.auth.AuthenticationRequest;
import com.gym.clients.auth.AuthenticationResponse;
import com.gym.clients.model.Client;

public interface AuthService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse registerClient(Client client);
}
