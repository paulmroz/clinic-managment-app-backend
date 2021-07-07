package pl.psk.clinicmanagement.presentation.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequest extends UserBaseRequest {
    @NotNull(message = "Email is required")
    protected String email;

    protected String type;

    protected String specialization;

    @DateTimeFormat
    protected String bornAt;

}
