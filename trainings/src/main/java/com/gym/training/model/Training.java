package com.gym.training.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.training.exception.Error;
import com.gym.training.exception.TrainingException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Training {
    @Id
    private String trainingCode;

    @NotBlank
    private String name;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Min(0)
    @NotNull
    private Integer maxParticipantsNumber;

    @Min(0)
    @NotNull
    private Integer participantsNumber;

    @NotNull
    private Status status;

    @NotNull
    private Category category;

    private List<TrainingMember> trainingMemberList = new ArrayList<>();


    public enum Status {
        ACTIVE,
        INACTIVE,
        FULL
    }

    public enum Category {
        FBW,
        CARDIO,
        BACK,
        CHEST,
        BICEPS_TRICEPS,
        LEGS,
        ABS,
        ARM
    }

    private void validateParticipants() {
        if (maxParticipantsNumber < participantsNumber) throw new TrainingException(Error.MORE_PARTICIPANTS_THAN_LIMIT);
    }

    private void validateStatus() {
        if (Status.ACTIVE.equals(status) && participantsNumber > maxParticipantsNumber) {
            throw new TrainingException(Error.TRAINING_CANNOT_HAVE_ACTIVE_STATUS);
        }
    }

    public void validateActiveStatus() {
        if (!Status.ACTIVE.equals(this.getStatus())) throw new TrainingException(Error.TRAINING_IS_NOT_ACTIVE);
    }

    public void validateDate(Training training) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isAfter(training.getDateTime()))
            throw new TrainingException(Error.WRONG_DATE);
    }

    public void validateTraining() {
        validateParticipants();
        validateStatus();
    }

    public void changeStatusToInactive() {
        if (Status.INACTIVE.equals(this.getStatus()) || Status.FULL.equals(this.getStatus()))
            throw new TrainingException(Error.TRAINING_IS_NOT_ACTIVE);
        setStatus(Status.INACTIVE);
    }

    public void updateTrainingPut(Training training) {
        setName(training.getName());
        setDescription(training.getDescription());
        setDateTime(training.getDateTime());
        setMaxParticipantsNumber(training.getMaxParticipantsNumber());
        setStatus(training.getStatus());
        setCategory(training.getCategory());
    }

    public void updateTrainingPatch(Training training) {
        if (!StringUtils.isEmpty(training.getName())) setName(training.getName());
        if (!StringUtils.isEmpty(training.getDescription())) setDescription(training.getDescription());
        if (training.getDateTime() != null) setDateTime(training.getDateTime());
        if (training.getMaxParticipantsNumber() != null) setMaxParticipantsNumber(training.getMaxParticipantsNumber());
        if (training.getStatus() != null) setStatus(training.getStatus());
        if (training.getCategory() != null) setCategory(training.getCategory());
        if (training.getMaxParticipantsNumber() != null && training.getMaxParticipantsNumber().equals(training.getParticipantsNumber()))setStatus(Status.FULL);
    }

    public void addParticipant() {
        participantsNumber++;
        if (participantsNumber.equals(maxParticipantsNumber)) setStatus(Status.FULL);
    }

    public void removeParticipant(String email) {
        List<String> emailsList = trainingMemberList.stream()
                .map(TrainingMember::getEmail).toList();
        if (!emailsList.contains(email))
            throw new TrainingException(Error.CLIENT_IS_NOT_ENROLLED_FOR_THIS_TRAINING);
        participantsNumber--;
        trainingMemberList.removeIf(member -> member.getEmail().equals(email));
        if (Status.FULL.equals(this.getStatus())) setStatus(Status.ACTIVE);
    }

    public boolean validateClient(String email) {
        List<String> emailsList = trainingMemberList.stream()
                .map(TrainingMember::getEmail).toList();
        if (emailsList.contains(email))
            throw new TrainingException(Error.CLIENT_IS_ALREADY_SIGNED_UP_FOR_THIS_TRAINING);
        return false;
    }

}
