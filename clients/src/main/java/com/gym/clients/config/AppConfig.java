package com.gym.clients.config;

import com.gym.clients.exception.ClientException;
import com.gym.clients.exception.Error;
import com.gym.clients.repository.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration

public class AppConfig {

    private final ClientRepository clientRepository;

    public AppConfig(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> clientRepository.findByEmail(username)
                .orElseThrow(() -> new ClientException(Error.THERE_IS_NOT_THAT_EMAIL_IN_THE_SYSTEM));
    }
}
