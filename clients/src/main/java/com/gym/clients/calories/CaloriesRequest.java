package com.gym.clients.calories;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CaloriesRequest {

    private double weight;

    private double height;

    private int age;

    private String gender;

    private String activity;
}
