package com.gym.clients.calories;

import com.gym.clients.exception.ClientException;
import com.gym.clients.exception.Error;

public class CaloriesResponse {


    public int caloriesCalculate(CaloriesRequest caloriesRequest) {
        double prop = 0.0;
        double result = 0.0;
        if (("Sedentary").equals(caloriesRequest.getActivity())) prop = 1.0;
        if (("Light").equals(caloriesRequest.getActivity())) prop = 1.2;
        if (("Moderate").equals(caloriesRequest.getActivity())) prop = 1.4;
        if (("Active").equals(caloriesRequest.getActivity())) prop = 1.6;
        if (("Very active").equals(caloriesRequest.getActivity())) prop = 1.8;
        if (("Pro").equals(caloriesRequest.getActivity())) prop = 2.0;

        if (("MALE").equals(caloriesRequest.getGender())) {
            result = prop * (66.5 + (13.7 * caloriesRequest.getWeight() + (5 * caloriesRequest.getHeight()) - (6.8 * caloriesRequest.getAge())));
        } else {
            result = prop * (66.5 + (9.6 * caloriesRequest.getWeight() + (1.85 * caloriesRequest.getHeight()) - (4.7 * caloriesRequest.getAge())));

        }
        return (int) result;
    }

    public void calorieCalculatorValidation(CaloriesRequest caloriesRequest) {
        if (caloriesRequest == null) throw new ClientException(Error.NO_WEIGHT);
        if (caloriesRequest.getWeight() == 0) throw new ClientException(Error.NO_WEIGHT);
        if (caloriesRequest.getHeight() == 0) throw new ClientException(Error.NO_HEIGHT);
        if (caloriesRequest.getAge() == 0) throw new ClientException(Error.NO_AGE);
    }
}
