package com.gym.training.service;


import com.gym.training.exception.Error;
import com.gym.training.exception.TrainingException;
import com.gym.training.model.ClientDto;
import com.gym.training.model.Training;
import com.gym.training.model.TrainingMember;
import com.gym.training.repository.TrainingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    private final ClientService clientService;

    public TrainingServiceImpl(TrainingRepository trainingRepository, ClientService clientService) {
        this.trainingRepository = trainingRepository;
        this.clientService = clientService;
    }

    @Override
    public Page<Training> getAllTraining(Pageable page) {
        return trainingRepository.findAll(page);
    }

    @Override
    public Training getTraining(String trainingCode) {
        return trainingRepository.findById(trainingCode).orElseThrow(() -> new TrainingException(Error.TRAINING_NOT_FOUND));
    }

    @Override
    public Page<Training> getTrainingByStatus(Training.Status status, Pageable page) {
        if (status != null) {
            return trainingRepository.findAllByStatus(status, page);
        } else throw new TrainingException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_STATUS);
    }

    @Override
    public Page<Training> getTrainingByCategory(Training.Category category, Pageable page) {
        if (category != null) {
            return trainingRepository.findAllByCategory(category, page);
        } else throw new TrainingException(Error.THERE_IS_NOT_REQUIRED_PARAMETER_CATEGORY);
    }

    @Override
    public Training addTraining(Training training) {
        if (trainingRepository.existsById(training.getTrainingCode()))
            throw new TrainingException(Error.TRAINING_ALREADY_EXIST);
        training.setParticipantsNumber(0);
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
        return trainingRepository.findById(trainingCode)
                .map(dbTraining -> {
                    training.validateTraining();
                    dbTraining.updateTrainingPut(training);
                    return trainingRepository.save(dbTraining);
                }).orElseThrow(() -> new TrainingException(Error.TRAINING_NOT_FOUND));
    }

    @Override
    public Training patchTraining(Training training, String trainingCode) {
        return trainingRepository.findById(trainingCode)
                .map(dbTraining -> {
                    training.validateTraining();
                    dbTraining.updateTrainingPatch(training);
                    return trainingRepository.save(dbTraining);
                }).orElseThrow(() -> new TrainingException(Error.TRAINING_NOT_FOUND));
    }

    @Override
    public void trainingSignup(String trainingCode, Long clientId) {
        Training training = getTraining(trainingCode);
        training.validateTraining();
        training.validateActiveStatus();
        ClientDto clientDto = clientService.getClientById(clientId);
        clientDto.validateClient();
        training.validateClient(clientDto.getEmail());
        training.addParticipant();
        training.getTrainingMemberList().add(new TrainingMember(clientDto.getFirstName(), clientDto.getEmail()));
        trainingRepository.save(training);
    }

    @Override
    public List<ClientDto> getTrainingMembers(String trainingCode) {
        Training training = getTraining(trainingCode);
        List<String> participantsEmails = training.getTrainingMemberList().stream()
                .map(TrainingMember::getEmail)
                .collect(Collectors.toList());
        return clientService.getClientsByEmail(participantsEmails);
    }

    @Override
    public List<Training> getClientTraining(String email) {
        List<Training> training = trainingRepository.findAll();
        List<Training> clientTraining = new ArrayList<>();
        for (Training train: training) {
            List<String> emails = train.getTrainingMemberList()
                    .stream()
                    .map(TrainingMember::getEmail)
                    .toList();
            if(emails.contains(email))clientTraining.add(train);
        }
        return clientTraining;
    }

    @Override
    public void removeParticipantFromTraining(String trainingCode, Long clientId) {
        Training training = getTraining(trainingCode);
        ClientDto clientDto = clientService.getClientById(clientId);
        training.removeParticipant(clientDto.getEmail());
        trainingRepository.save(training);
    }

    @Override
    public void finishTraining(String trainingCode) {
        Training training = getTraining(trainingCode);
        training.changeStatusToInactive();
        trainingRepository.save(training);
    }
}
