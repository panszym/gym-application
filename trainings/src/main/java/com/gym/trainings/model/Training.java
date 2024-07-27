package com.gym.trainings.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class Training {
    @Id
    private Long trainingCode;

    @NotBlank
    private String name;

    private String description;

    private LocalDateTime dateTime;

    @Min(0)
    @NotNull
    private Integer maxParticipantsNumber;

    @Min(0)
    @NotNull
    private Integer participantsNumber;

    @NotNull
    private Status status;


    public enum Status{
        ACTIVE,
        INACTIVE
    }
}
