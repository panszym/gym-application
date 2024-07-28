package com.gym.training.controller;

import com.gym.training.model.Training;
import com.gym.training.service.TrainingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    List<Training> getAllTraining() {
        return trainingService.getAllTraining();
    }

    @GetMapping("/{trainingCode}")
    Training getTraining(@PathVariable String trainingCode) {
        return trainingService.getTraining(trainingCode);
    }

    @GetMapping("/status")
    List<Training> getTrainingByStatus(@RequestParam Training.Status status) {
        return trainingService.getTrainingByStatus(status);
    }

    @PostMapping
    Training addTraining(@RequestBody @Valid Training training) {
        return trainingService.addTraining(training);
    }

    @DeleteMapping("/{trainingCode}")
    void deleteTraining(@PathVariable String trainingCode) {
        trainingService.deleteTraining(trainingCode);
    }

    @PutMapping("/{trainingCode}")
    Training putTraining(@RequestBody @Valid Training training, @PathVariable String trainingCode) {
        return training;
    }

    @PatchMapping("/{trainingCode}")
    Training patchTraining(@RequestBody Training training, @PathVariable String trainingCode) {
        return training;
    }


}
