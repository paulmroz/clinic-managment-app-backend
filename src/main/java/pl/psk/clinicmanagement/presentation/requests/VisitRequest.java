package pl.psk.clinicmanagement.presentation.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class VisitRequest {
    @NotNull
    private String name;

    @NotNull
    private String scheduledFor;

    @NotNull
    private Long doctorId;

    @NotNull
    private Long clientId;

    private BigDecimal price;

    private String isPayed;

    private String externalId;
}
