package com.gym.training.service;

import com.gym.training.exception.Error;
import com.gym.training.exception.TrainingException;
import com.gym.training.model.Training;
import com.gym.training.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Training> getAllTraining() {
        return trainingRepository.findAll();
    }

    @Override
    public Training getTraining(String trainingCode) {
        return trainingRepository.findById(trainingCode).orElseThrow(() -> new TrainingException(Error.TRAINING_NOT_FOUND));
    }

    @Override
    public List<Training> getTrainingByStatus(Training.Status status) {
        return trainingRepository.findAllByStatus(status);
    }

    @Override
    public Training addTraining(Training training) {
        if (trainingRepository.existsById(training.getTrainingCode()))
            throw new TrainingException(Error.TRAINING_ALREADY_EXIST);
        training.validateTraining();
        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(String trainingCode) {
        if (!trainingRepository.existsById(trainingCode))
            throw new TrainingException(Error.TRAINING_NOT_FOUND);
        trainingRepository.deleteById(trainingCode);
    }

    @Override
    public Training putTraining(Training training, String trainingCode) {
        return null;
    }

    @Override
    public Training patchTraining(Training training, String trainingCode) {
        return null;
    }
}
