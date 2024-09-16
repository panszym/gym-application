package com.gym.clients.bmi;

import com.gym.clients.exception.ClientException;
import com.gym.clients.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BMIResponse {

    private BigDecimal result;

    private String message;

    public BMIResponse BMICalculate(BMIRequest bmiRequest) {
        double result = 0.0;
        result = (bmiRequest.getWeight() / (bmiRequest.getHeight() / 100 * bmiRequest.getHeight() / 100));
        BigDecimal responseResult = new BigDecimal(result);
        responseResult = responseResult.setScale(2, RoundingMode.HALF_UP);
        if (result < 16.0) return new BMIResponse(responseResult, Response.SEVERELY_UNDERWEIGHT.getMessage());
        if (result >= 16.0 && result < 18.5) return new BMIResponse(responseResult, Response.UNDERWEIGHT.getMessage());
        if (result >= 18.5 && result < 25.0) return new BMIResponse(responseResult, Response.NORMAL.getMessage());
        if (result >= 25.0 && result < 30.0) return new BMIResponse(responseResult, Response.OVERWEIGHT.getMessage());
        if (result >= 30.0 && result < 35.0)
            return new BMIResponse(responseResult, Response.MODERATELY_OBESE.getMessage());
        if (result >= 35.0 && result < 40.0)
            return new BMIResponse(responseResult, Response.SEVERELY_OBESE.getMessage());
        else return new BMIResponse(responseResult, Response.MORBIDLY_OBESE.getMessage());
    }

    public void validateRequest(BMIRequest request) {
        if (request == null) throw new ClientException(Error.NO_WEIGHT);
        if (request.getWeight() == 0) throw new ClientException(Error.NO_WEIGHT);
        if (request.getHeight() == 0) throw new ClientException(Error.NO_HEIGHT);

    }
}
