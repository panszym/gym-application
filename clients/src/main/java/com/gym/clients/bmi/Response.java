package com.gym.clients.bmi;

public enum Response {

    SEVERELY_UNDERWEIGHT("Severely underweight"),
    UNDERWEIGHT("Underweight"),
    NORMAL("Normal"),
    OVERWEIGHT("Overweight"),
    MODERATELY_OBESE("Moderately obese"),
    SEVERELY_OBESE("Severely obese"),
    MORBIDLY_OBESE("Morbidly obese");

    private String message;

    Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
