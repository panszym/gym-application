package com.gym.training.service;

import com.gym.training.exception.TrainingException;
import com.gym.training.model.ClientDto;
import com.gym.training.model.Training;
import com.gym.training.model.TrainingMember;
import com.gym.training.repository.TrainingRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainingServiceImplTest {


    @Test
    void getTrainingByTrainingCode_TrainingDoesNotExist_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.empty());
        //then
        var exception = catchThrowable(() -> toTest.getTraining("test"));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void getTrainingByTrainingCode_TrainingExists_shouldReturnNotNull() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var mockTraining = mock(Training.class);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(mockTraining));
        //then
        var result = toTest.getTraining("test");
        //then
        assertNotNull(result);
    }

    @Test
    void getTrainingByStatus_activeStatus_shouldReturnTrainingByStatus() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockTraining1 = mock(Training.class);
        var mockTraining2 = mock(Training.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        //when
        when(mockTrainingRepository.findAllByStatus(Training.Status.ACTIVE)).thenReturn(List.of(mockTraining1, mockTraining2));
        var result = toTest.getTrainingByStatus(Training.Status.ACTIVE);
        //then
        assertNotNull(result, "Response is null");
    }

    @Test
    void getTrainingByStatus_noStatus_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockTraining1 = mock(Training.class);
        var mockTraining2 = mock(Training.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        //when
        when(mockTrainingRepository.findAllByStatus(any())).thenReturn(List.of(mockTraining1, mockTraining2));
        var exception = catchThrowable(() -> toTest.getTrainingByStatus(null));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void addTraining_trainingWithTheSameTrainingCodeExistInSystem_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        //when
        when(mockTrainingRepository.existsById(testTraining.getTrainingCode())).thenReturn(true);
        var exception = catchThrowable(() -> toTest.addTraining(testTraining));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void addTraining_trainingWithTheSameTrainingCodeDoesNotExistInSystem_statusActiveMaxParticipantEqualsZero_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        //when
        testTraining.setMaxParticipantsNumber(0);
        when(mockTrainingRepository.existsById(testTraining.getTrainingCode())).thenReturn(false);
        var exception = catchThrowable(() -> toTest.addTraining(testTraining));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }


    @Test
    void putTraining_TrainingDoesNotExistInSystem_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.putTraining(testTraining, anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void putTraining_TrainingExistsInSystem_wantChangeStatusToActiveWhenThereIsNoMorePlaces_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        var mockTraining = mock(Training.class);
        //when
        testTraining.setMaxParticipantsNumber(5);
        testTraining.setParticipantsNumber(5);
        testTraining.setStatus(Training.Status.ACTIVE);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(mockTraining));
        var exception = catchThrowable(() -> toTest.putTraining(testTraining, anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void putTraining_TrainingExistsInSystem_wantChangeMaxCountOfParticipantsLessThanEnrolledParticipants_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        var mockTraining = mock(Training.class);
        //when
        testTraining.setMaxParticipantsNumber(4);
        testTraining.setParticipantsNumber(5);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(mockTraining));
        var exception = catchThrowable(() -> toTest.putTraining(testTraining, anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void patchTraining_TrainingDoesNotExistInSystem_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.patchTraining(testTraining, anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void patchTraining_TrainingExistsInSystem_wantChangeStatusToActiveWhenThereIsNoMorePlaces_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        var mockTraining = mock(Training.class);
        //when
        testTraining.setMaxParticipantsNumber(5);
        testTraining.setParticipantsNumber(5);
        testTraining.setStatus(Training.Status.ACTIVE);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(mockTraining));
        var exception = catchThrowable(() -> toTest.patchTraining(testTraining, anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void patchTraining_TrainingExistsInSystem_wantChangeMaxCountOfParticipantsLessThanEnrolledParticipants_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        var mockTraining = mock(Training.class);
        //when
        testTraining.setMaxParticipantsNumber(4);
        testTraining.setParticipantsNumber(5);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(mockTraining));
        var exception = catchThrowable(() -> toTest.patchTraining(testTraining, anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void trainingSignup_trainingDoesNotExistInTheSystem_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.trainingSignup(anyString(), 1L));
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void trainingSignup_trainingExistsInTheSystem_clientDoesNotExistInSystemNullValue_shouldThrowNullPointerException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        var mockTraining = mock(Training.class);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(mockTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(null);
        var exception = catchThrowable(() -> toTest.trainingSignup(anyString(), 1L));
        assertThat(exception)
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void trainingSignup_trainingExistsInTheSystem_clientExistsInSystem_clientStatusActive_premiumTicket_trainingStatusIsInactive_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        //when
        testClientDto.setStatus(ClientDto.Status.ACTIVE);
        testTraining.setStatus(Training.Status.INACTIVE);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        var exception = catchThrowable(() -> toTest.trainingSignup(anyString(), 1L));
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void trainingSignup_trainingExistsInTheSystem_clientExistsInSystem_clientStatusActive_premiumTicket_trainingStatusIsFULL_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        //when
        testClientDto.setStatus(ClientDto.Status.ACTIVE);
        testTraining.setStatus(Training.Status.FULL);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        var exception = catchThrowable(() -> toTest.trainingSignup(anyString(), 1L));
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void trainingSignup_trainingExistsInTheSystem_clientExistsInSystem_clientStatusInactive_premiumTicket_trainingStatusIsActive_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        //when
        testClientDto.setStatus(ClientDto.Status.INACTIVE);
        testTraining.setStatus(Training.Status.ACTIVE);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        var exception = catchThrowable(() -> toTest.trainingSignup(anyString(), 1L));
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void trainingSignup_trainingExistsInTheSystem_clientExistsInSystem_clientStatusActive_normalTicket_trainingStatusIsActive_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        //when
        testClientDto.setTicket(ClientDto.Ticket.NORMAL);
        testTraining.setStatus(Training.Status.ACTIVE);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        var exception = catchThrowable(() -> toTest.trainingSignup(anyString(), 1L));
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void getTrainingMembers_trainingDoesNotExistInTheSystem_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        var mockClientDTO1 = mock(ClientDto.class);
        var mockClientDTO2 = mock(ClientDto.class);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.empty());
        when(mockClientService.getClientsByEmail(anyList())).thenReturn(List.of(mockClientDTO1, mockClientDTO2));
        var exception = catchThrowable(() -> toTest.getTrainingMembers(anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void getTrainingMembers_trainingExistsInTheSystem_shouldReturnNotNull() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        var testTraining = getTestTraining();
        var mockClientDTO1 = mock(ClientDto.class);
        var mockClientDTO2 = mock(ClientDto.class);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientsByEmail(anyList())).thenReturn(List.of(mockClientDTO1, mockClientDTO2));
        var result = toTest.getTrainingMembers(anyString());
        //then
        assertNotNull(result);
    }

    @Test
    void removeParticipantFromTraining_trainingDoesNotExist_clientExists_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        //when
        testTraining.getTrainingMemberList().add(new TrainingMember(testClientDto.getFirstName(), testClientDto.getEmail()));
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.empty());
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        var exception = catchThrowable(() -> toTest.removeParticipantFromTraining(testTraining.getTrainingCode(), anyLong()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void removeParticipantFromTraining_trainingExists_clientExists_clientIsNotEnrolledToTraining_shouldThrowTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        var exception = catchThrowable(() -> toTest.removeParticipantFromTraining(testTraining.getTrainingCode(), anyLong()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void removeParticipantFromTraining_trainingExists_clientExists_clientIsEnrolledToTraining_trainingStatusFULL_shouldChangeStatusToActive() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        //when
        testTraining.getTrainingMemberList().add(new TrainingMember(testClientDto.getFirstName(), testClientDto.getEmail()));
        testTraining.setMaxParticipantsNumber(5);
        testTraining.setParticipantsNumber(5);
        testTraining.setStatus(Training.Status.FULL);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        toTest.removeParticipantFromTraining(testTraining.getTrainingCode(), anyLong());
        var resultParticipantNumber = testTraining.getParticipantsNumber();
        var resultStatus = testTraining.getStatus();
        //then
        assertThat(resultParticipantNumber)
                .isEqualTo(4);
        assertThat(resultStatus)
                .isEqualTo(Training.Status.ACTIVE);

    }

    @Test
    void removeParticipantFromTraining_trainingExists_clientExists_clientIsEnrolledToTraining_shouldRemoveParticipantFromTraining() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var mockClientService = mock(ClientService.class);
        var testTraining = getTestTraining();
        var testClientDto = getTestClientDto();
        var toTest = new TrainingServiceImpl(mockTrainingRepository, mockClientService);
        //when
        testTraining.getTrainingMemberList().add(new TrainingMember(testClientDto.getFirstName(), testClientDto.getEmail()));
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        when(mockClientService.getClientById(anyLong())).thenReturn(testClientDto);
        var participantNumberBeforeRemove = testTraining.getTrainingMemberList().size();
        toTest.removeParticipantFromTraining(testTraining.getTrainingCode(), anyLong());
        var result = testTraining.getTrainingMemberList().size();
        //then
        assertThat(participantNumberBeforeRemove - 1)
                .isEqualTo(result);
    }

    @Test
    void finishTraining_trainingDoesNotExist_shouldReturnTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.empty());
        var exception = catchThrowable(() -> toTest.finishTraining(anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void finishTraining_trainingExistsWithStatusFULL_shouldReturnTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        //when
        testTraining.setStatus(Training.Status.FULL);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        var exception = catchThrowable(() -> toTest.finishTraining(anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void finishTraining_trainingExistsWithStatusINACTIVE_shouldReturnTrainingException() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        //when
        testTraining.setStatus(Training.Status.INACTIVE);
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        var exception = catchThrowable(() -> toTest.finishTraining(anyString()));
        //then
        assertThat(exception)
                .isInstanceOf(TrainingException.class);
    }

    @Test
    void finishTraining_trainingExistsWithStatusActive_trainingShouldBecomeFinished() {
        //given
        var mockTrainingRepository = mock(TrainingRepository.class);
        var toTest = new TrainingServiceImpl(mockTrainingRepository, null);
        var testTraining = getTestTraining();
        //when
        when(mockTrainingRepository.findById(anyString())).thenReturn(Optional.of(testTraining));
        toTest.finishTraining(testTraining.getTrainingCode());
        var result = testTraining.getStatus();
        //then
        assertThat(result)
                .isEqualTo(Training.Status.INACTIVE);
    }

    Training getTestTraining() {
        Training training = new Training();
        training.setTrainingCode("test");
        training.setStatus(Training.Status.ACTIVE);
        training.setDescription("Test description");
        training.setParticipantsNumber(0);
        training.setDateTime(LocalDateTime.parse("2024-10-23T17:00:00.000"));
        training.setMaxParticipantsNumber(10);
        return training;
    }

    ClientDto getTestClientDto() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("testFirstName");
        clientDto.setLastName("testLastName");
        clientDto.setEmail("testmail@gmail.com");
        clientDto.setStatus(ClientDto.Status.ACTIVE);
        clientDto.setTicket(ClientDto.Ticket.PREMIUM);
        return clientDto;
    }
}