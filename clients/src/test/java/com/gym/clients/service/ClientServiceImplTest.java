package com.gym.clients.service;

import com.gym.clients.exception.ClientException;
import com.gym.clients.model.Client;
import com.gym.clients.model.Role;
import com.gym.clients.repository.ClientRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {


    @Test
    void getClientById_clientIsNotInTheSystem_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.getClientById(1L));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void getClientById_clientIsInTheSystem_shouldGetClientById() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var mockClient = mock(Client.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.of(mockClient));
        var result = toTest.getClientById(1L);
        //then
        assertNotNull(result, "Response is null");
    }

    @Test
    void getClientByIdAdmin_clientIsNotInTheSystem_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.getClientByIdAdmin(1L));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void getClientByIdAdmin_clientIsInTheSystem_shouldGetClientById() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var mockClient = mock(Client.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.of(mockClient));
        var result = toTest.getClientByIdAdmin(1L);
        //then
        assertNotNull(result, "Response is null");
    }

    @Test
    void getClientByIdFeign_clientIsNotInTheSystem_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.getClientByIdFeign(1L));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void getClientByIdFeign_clientIsInTheSystem_shouldGetClientById() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var mockClient = mock(Client.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.of(mockClient));
        var result = toTest.getClientByIdFeign(1L);
        //then
        assertNotNull(result, "Response is null");
    }

    @Test
    void getClientsByStatus_noStatus_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        var mockClient1 = mock(Client.class);
        var mockClient2 = mock(Client.class);
        //when
        when(mockClientRepository.findAllByStatus(any())).thenReturn(List.of(mockClient1, mockClient2));
        var exception = catchThrowable(() -> toTest.getClientsByStatus(null));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void getClientsByStatus_ActiveStatus_shouldReturnClientByStatus() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var mockClient1 = mock(Client.class);
        var mockClient2 = mock(Client.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findAllByStatus(Client.Status.ACTIVE)).thenReturn(List.of(mockClient1, mockClient2));
        var result = toTest.getClientsByStatus(Client.Status.ACTIVE);
        //then
        assertNotNull(result, "Response is null");
    }

    @Test
    void getClientsByTicket_noTicket_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        var exception = catchThrowable(() -> toTest.getClientsByTicket(null));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void getClientsByTicket_PremiumTicket_shouldReturnClientByTicket() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var mockClient1 = mock(Client.class);
        var mockClient2 = mock(Client.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findAllByTicket(Client.Ticket.PREMIUM)).thenReturn(List.of(mockClient1, mockClient2));
        var result = toTest.getClientsByTicket(Client.Ticket.PREMIUM);
        //then
        assertNotNull(result, "Response is null");
    }

    @Test
    void putClient_ClientIsNotInSystem_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        var testClient = getTestClient();
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.putClient(testClient, 1L));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void putClient_ClientIsInSystem_DifferentEmail_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        var testClient = getTestClient();
        var testClient1 = getTestClient();
        testClient1.setEmail("Kowalski@gmail.com");
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.of(testClient));
        var exception = catchThrowable(() -> toTest.putClient(testClient1, testClient1.getId()));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void patchClient_ClientIsNotInSystem_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        var testClient = getTestClient();
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.patchClient(testClient, 1L));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void toggleStatus_clientIsNotInSystem_shouldThrowClientException() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var toTest = new ClientServiceImpl(mockClientRepository);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.getClientById(1L));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void toggleStatus_clientIsInSystem_statusFromActiveToInactive() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var testClient = getTestClient();
        var toTest = new ClientServiceImpl(mockClientRepository);
        testClient.setStatus(Client.Status.ACTIVE);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.of(testClient));
        toTest.toggleStatus(1L);
        var result = testClient.getStatus();
        //then
        assertThat(result)
                .isNotEqualTo(Client.Status.ACTIVE);
    }

    @Test
    void toggleStatus_clientIsInSystem_statusFromInactiveToActive() {
        //given
        var mockClientRepository = mock(ClientRepository.class);
        var testClient = getTestClient();
        var toTest = new ClientServiceImpl(mockClientRepository);
        testClient.setStatus(Client.Status.INACTIVE);
        //when
        when(mockClientRepository.findById(anyLong())).thenReturn(Optional.of(testClient));
        toTest.toggleStatus(1L);
        var result = testClient.getStatus();
        //then
        assertThat(result)
                .isNotEqualTo(Client.Status.INACTIVE);
    }

    Client getTestClient() {
        Client client = new Client();
        client.setFirstName("Jan");
        client.setLastName("Kowalski");
        client.setEmail("jKowalski@gmail.com");
        client.setStatus(Client.Status.ACTIVE);
        client.setRole(Role.CLIENT);
        return client;
    }
}