package pl.psk.clinicmanagement.presentation.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientRequest extends UserRequest {
    @NotNull
    @DateTimeFormat
    private String bornAt;

}