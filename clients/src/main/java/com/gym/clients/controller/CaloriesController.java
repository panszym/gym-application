package com.gym.clients.controller;

import com.gym.clients.calories.CaloriesRequest;
import com.gym.clients.service.CaloriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/clients/calories")
public class CaloriesController {

    private static final Logger logger = LoggerFactory.getLogger(CaloriesController.class);
    private final CaloriesService caloriesService;

    public CaloriesController(CaloriesService caloriesService) {
        this.caloriesService = caloriesService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    public int caloriesCalculate(@RequestBody CaloriesRequest caloriesRequest) {
        logger.info("Calorie calculator info.");
        return caloriesService.calorieCalculate(caloriesRequest);
    }


}
