package com.gym.training.model;

import com.gym.training.exception.Error;
import com.gym.training.exception.TrainingException;
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
    private String trainingCode;

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


    public enum Status {
        ACTIVE,
        INACTIVE
    }

    private void validateParticipants() {
        if (maxParticipantsNumber < participantsNumber) throw new TrainingException(Error.MORE_PARTICIPANTS_THAN_LIMIT);
    }

    private void validateStatus() {
        if (Status.ACTIVE.equals(status) && participantsNumber.equals(maxParticipantsNumber)) {
            throw new TrainingException(Error.TRAINING_CANNOT_HAVE_ACTIVE_STATUS);
        }
    }

    public void validateTraining() {
        validateParticipants();
        validateStatus();
    }
}
