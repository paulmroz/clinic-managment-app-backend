package pl.psk.clinicmanagement.presentation.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeVisitDateRequest {
    @NotNull
    private String scheduledFor;
}
