package com.gym.training.controller;

import com.gym.training.model.ClientDto;
import com.gym.training.model.Training;
import com.gym.training.service.TrainingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {
    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    List<Training> getAllTraining() {
        logger.info("Displayed all available training.");
        return trainingService.getAllTraining();
    }

    @GetMapping("/{trainingCode}")
    Training getTraining(@PathVariable String trainingCode) {
        logger.info("Displayed training by trainingCode.");
        return trainingService.getTraining(trainingCode);
    }

    @GetMapping("/status")
    List<Training> getTrainingByStatus(@RequestParam(required = false) Training.Status status) {
        logger.info("Displayed list of training by status.");
        return trainingService.getTrainingByStatus(status);
    }

    @GetMapping("/{trainingCode}/participants")
    public List<ClientDto> getTrainingMembers(@PathVariable String trainingCode){
        logger.info("Display training participants");
        return trainingService.getTrainingMembers(trainingCode);
    }

    @PostMapping
    Training addTraining(@RequestBody @Valid Training training) {
        logger.info("Added training to database.");
        return trainingService.addTraining(training);
    }

    @DeleteMapping("/{trainingCode}")
    void deleteTraining(@PathVariable String trainingCode) {
        logger.info("Remove training from database.");
        trainingService.deleteTraining(trainingCode);
    }

    @PutMapping("/{trainingCode}")
    Training putTraining(@RequestBody @Valid Training training, @PathVariable String trainingCode) {
        logger.info("Updated training.");
        return trainingService.putTraining(training, trainingCode);
    }

    @PatchMapping("/{trainingCode}")
    Training patchTraining(@RequestBody Training training, @PathVariable String trainingCode) {
        logger.info("Updated training.");
        return trainingService.patchTraining(training, trainingCode);
    }

    @PostMapping("/{trainingCode}/clients/{clientId}")
    ResponseEntity<?> trainingSignup(@PathVariable String trainingCode, @PathVariable Long clientId){
        trainingService.trainingSignup(trainingCode, clientId);
        logger.info("Client signed up to the training");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/finish/{trainingCode}")
    ResponseEntity<?> finishTraining(@PathVariable String trainingCode){
        trainingService.finishTraining(trainingCode);
        logger.info("Finish training");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/{trainingCode}/clients/{clientId}")
    ResponseEntity<?> removeParticipantFromTraining(@PathVariable String trainingCode, @PathVariable Long clientId){
        trainingService.removeParticipantFromTraining(trainingCode, clientId);
        logger.info("Remove participant from training");
        return ResponseEntity.ok().build();
    }

}
