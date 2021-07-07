package pl.psk.clinicmanagement.presentation.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddVisitDetailsRequest {
    @NotNull
    private String details;
}

