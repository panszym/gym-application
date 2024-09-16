package com.gym.clients.service;

import com.gym.clients.bmi.BMIRequest;
import com.gym.clients.exception.ClientException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class BMIServiceTest {

    @Test
    void BMICalculate_thereIsNoClientWeightInTheRequest_shouldReturnClientException() {
        //given
        var mockBMIRequest = mock(BMIRequest.class);
        var toTest = new BMIService();
        mockBMIRequest.setHeight(0);
        mockBMIRequest.setWeight(50);
        //when
        var exception = catchThrowable(() -> toTest.BMICalculate(mockBMIRequest));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void BMICalculate_thereIsNoClientHeightInTheRequest_shouldReturnClientException() {
        //given
        var mockBMIRequest = mock(BMIRequest.class);
        var toTest = new BMIService();
        mockBMIRequest.setWeight(0);
        mockBMIRequest.setHeight(150);
        //when
        var exception = catchThrowable(() -> toTest.BMICalculate(mockBMIRequest));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void BMICalculate_thereIsNoClientDetailsInTheRequest_shouldReturnClientException() {
        //given
        var mockBMIRequest = mock(BMIRequest.class);
        var toTest = new BMIService();
        //when
        var exception = catchThrowable(() -> toTest.BMICalculate(mockBMIRequest));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

}