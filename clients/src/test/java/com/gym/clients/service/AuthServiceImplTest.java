package com.gym.clients.service;

import com.gym.clients.config.JwtService;
import com.gym.clients.exception.ClientException;
import com.gym.clients.model.Client;
import com.gym.clients.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    @Test
    void addClient_clientIsAlreadyInSystem_shouldReturnClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var mockJwtService = mock(JwtService.class);
        var mockAuthenticationManager = mock(AuthenticationManager.class);
        var toTest = new AuthServiceImpl(mockClientRepository, null, mockJwtService, mockAuthenticationManager);
        var mockClient = mock(Client.class);
        //when
        when(mockClientRepository.existsByEmail(mockClient.getEmail())).thenReturn(true);
        var exception = catchThrowable(() -> toTest.registerClient(mockClient));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }
}
