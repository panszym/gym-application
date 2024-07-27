package com.gym.trainings.service;

import com.gym.trainings.model.Training;

import java.util.List;

public interface TrainingService {

    List<Training> getTrainings();

    Training getTraining(Long trainingCode);

    Training addTraining(Training training);

    void deleteTraining(Long trainingCode);

    Training putTraining(Training training, Long trainingCode);

    Training patchTraining(Training training, Long trainingCode);


}
