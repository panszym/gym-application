package com.gym.training.model;

import com.gym.training.exception.Error;
import com.gym.training.exception.TrainingException;
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

    public void validateClient() {
        if (!Status.ACTIVE.equals(this.getStatus())) throw new TrainingException(Error.CLIENT_IS_NOT_ACTIVE);
        if (!(Ticket.PREMIUM.equals(this.getTicket()) || Ticket.MASTER.equals(this.getTicket())))
            throw new TrainingException(Error.CLIENT_DOES_NOT_HAVE_PREMIUM_OR_MASTER_TICKET);
    }

}
