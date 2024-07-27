package com.gym.trainings.controller;

import com.gym.trainings.model.Training;
import com.gym.trainings.service.TrainingService;
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
    List<Training> getAllTraining(){
        return trainingService.getAllTraining();
    }

    @GetMapping("/{trainingCode}")
    Training getTraining(@PathVariable Long trainingCode){
        return trainingService.getTraining(trainingCode);
    }

    @PostMapping
    Training addTraining(@RequestBody @Valid Training training){
        return trainingService.addTraining(training);
    }

    @DeleteMapping("/{trainingCode}")
    void deleteTraining(@PathVariable Long trainingCode){
        trainingService.deleteTraining(trainingCode);
    }

    @PutMapping("/{trainingCode}")
    Training putTraining(@RequestBody @Valid Training training, @PathVariable Long trainingCode){
        return training;
    }

    @PutMapping("/{trainingCode}")
    Training patchTraining(@RequestBody Training training, @PathVariable Long trainingCode){
        return training;
    }


}
