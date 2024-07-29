package com.gym.training.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TrainingMember {
    @NotBlank
    private String firstname;
    @NotBlank
    private String email;

    public TrainingMember(String firstname, String email) {
        this.firstname = firstname;
        this.email = email;
    }

}
