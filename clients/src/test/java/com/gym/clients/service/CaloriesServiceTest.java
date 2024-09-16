package com.gym.clients.service;

import com.gym.clients.calories.CaloriesRequest;
import com.gym.clients.exception.ClientException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class CaloriesServiceTest {

    @Test
    void CalorieCalculate_thereIsNoClientWeightInTheRequest_shouldReturnClientException() {
        //given
        var mockCalorieRequest = mock(CaloriesRequest.class);
        var toTest = new CaloriesService();
        mockCalorieRequest.setHeight(175);
        mockCalorieRequest.setAge(27);
        mockCalorieRequest.setGender("MALE");
        mockCalorieRequest.setActivity("Light");
        //when
        var exception = catchThrowable(() -> toTest.calorieCalculate(mockCalorieRequest));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void CalorieCalculate_thereIsNoClientHeightInTheRequest_shouldReturnClientException() {
        //given
        var mockCalorieRequest = mock(CaloriesRequest.class);
        var toTest = new CaloriesService();
        mockCalorieRequest.setWeight(50);
        mockCalorieRequest.setAge(27);
        mockCalorieRequest.setGender("MALE");
        mockCalorieRequest.setActivity("Light");
        //when
        var exception = catchThrowable(() -> toTest.calorieCalculate(mockCalorieRequest));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

    @Test
    void CalorieCalculate_thereIsNoClientAgeInTheRequest_shouldReturnClientException() {
        //given
        var mockCalorieRequest = mock(CaloriesRequest.class);
        var toTest = new CaloriesService();
        mockCalorieRequest.setWeight(50);
        mockCalorieRequest.setHeight(175);
        mockCalorieRequest.setGender("MALE");
        mockCalorieRequest.setActivity("Light");
        //when
        var exception = catchThrowable(() -> toTest.calorieCalculate(mockCalorieRequest));
        //then
        assertThat(exception)
                .isInstanceOf(ClientException.class);
    }

}