package com.gym.training.service;

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



}