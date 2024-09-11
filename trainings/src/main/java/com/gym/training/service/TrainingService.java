package com.gym.training.service;

import com.gym.training.model.ClientDto;
import com.gym.training.model.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrainingService {

    Page<Training> getAllTraining(Pageable page);

    Training getTraining(String trainingCode);

    Page<Training> getTrainingByStatus(Training.Status status, Pageable page);


    Page<Training> getTrainingByCategory(Training.Category category, Pageable page);

    Training addTraining(Training training);

    void deleteTraining(String trainingCode);

    Training putTraining(Training training, String trainingCode);

    Training patchTraining(Training training, String trainingCode);

    void trainingSignup(String trainingCode, Long clientId);
    List<ClientDto> getTrainingMembers(String trainingCode);

    void removeParticipantFromTraining(String trainingCode, Long clientId);

    void finishTraining(String trainingCode);



}
