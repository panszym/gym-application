package com.gym.clients.controller;

import com.gym.clients.bmi.BMIRequest;
import com.gym.clients.bmi.BMIResponse;
import com.gym.clients.service.BMIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/clients/bmi")
public class BMIController {
    private static final Logger logger = LoggerFactory.getLogger(BMIController.class);
    private final BMIService bmiService;

    public BMIController(BMIService bmiService) {
        this.bmiService = bmiService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
     BMIResponse BMICalculate(@RequestBody BMIRequest bmiRequest) {
        logger.info("Bmi clients info.");
        return bmiService.BMICalculate(bmiRequest);
    }
}
