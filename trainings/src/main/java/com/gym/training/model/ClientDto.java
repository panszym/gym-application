package com.gym.training.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotNull
    private Status status;
    @NotNull
    private Ticket ticket;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Ticket {
        NORMAL,
        PREMIUM,
        MASTER
    }
}
