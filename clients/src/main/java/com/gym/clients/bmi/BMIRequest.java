package com.gym.clients.bmi;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BMIRequest {

    private double height;

    private double weight;
}
