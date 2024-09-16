package com.gym.clients.service;

import com.gym.clients.bmi.BMIRequest;
import com.gym.clients.bmi.BMIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BMIService {

    private static final Logger logger = LoggerFactory.getLogger(BMIService.class);


    public BMIResponse BMICalculate(BMIRequest bmiRequest) {
        logger.info("Bmi clients info service.");
        BMIResponse bmiResponse = new BMIResponse();
        bmiResponse.validateRequest(bmiRequest);
        return bmiResponse.BMICalculate(bmiRequest);
    }
}
