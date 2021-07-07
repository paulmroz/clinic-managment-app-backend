package pl.psk.clinicmanagement.presentation.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class TreatmentsRequest {
        @NotNull
        private String name;
        @NotNull
        private String cost;
}