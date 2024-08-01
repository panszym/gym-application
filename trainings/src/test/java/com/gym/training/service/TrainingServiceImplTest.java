package com.gym.training.service;

import com.gym.training.exception.TrainingException;
import com.gym.training.model.Training;
import com.gym.training.repository.TrainingRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void trainingSignup() {
    }

    @Test
    void getTrainingMembers() {
    }

    @Test
    void removeParticipantFromTraining() {
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
}