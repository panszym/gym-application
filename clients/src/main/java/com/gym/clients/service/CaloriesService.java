package com.gym.clients.service;

import com.gym.clients.calories.CaloriesRequest;
import com.gym.clients.calories.CaloriesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CaloriesService {

    private static final Logger logger = LoggerFactory.getLogger(CaloriesService.class);


    public int calorieCalculate(CaloriesRequest caloriesRequest) {
        logger.info("Calorie calculator info service.");
        CaloriesResponse caloriesResponse = new CaloriesResponse();
        caloriesResponse.calorieCalculatorValidation(caloriesRequest);
        return caloriesResponse.caloriesCalculate(caloriesRequest);
    }
}
