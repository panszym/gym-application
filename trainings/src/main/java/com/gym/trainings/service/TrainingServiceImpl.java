package com.gym.trainings.service;

import com.gym.trainings.model.Training;
import com.gym.trainings.repository.TrainingRepository;

import java.util.List;

public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public Training getTraining(Long trainingCode) {
        return trainingRepository.findById(trainingCode).orElseThrow();
    }

    @Override
    public Training addTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(Long trainingCode) {
        trainingRepository.deleteById(trainingCode);
    }

    @Override
    public Training putTraining(Training training, Long trainingCode) {
        return null;
    }

    @Override
    public Training patchTraining(Training training, Long trainingCode) {
        return null;
    }
}
