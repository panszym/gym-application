package com.gym.training.service;

import com.gym.training.model.ClientDto;
import com.gym.training.model.Training;

import java.util.List;

public interface TrainingService {

    List<Training> getAllTraining();

    Training getTraining(String trainingCode);

    List<Training> getTrainingByStatus(Training.Status status);

    Training addTraining(Training training);

    void deleteTraining(String trainingCode);

    Training putTraining(Training training, String trainingCode);

    Training patchTraining(Training training, String trainingCode);

    void trainingSignup(String trainingCode, Long clientId);
    List<ClientDto> getTrainingMembers(String trainingCode);

    void removeParticipantFromTraining(String trainingCode, String email);

    void finishTraining(String trainingCode);



}
