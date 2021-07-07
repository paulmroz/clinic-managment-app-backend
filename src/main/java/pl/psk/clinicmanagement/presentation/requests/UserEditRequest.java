package pl.psk.clinicmanagement.presentation.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserEditRequest {
    protected String password;

    protected String passwordConfirmation;

    protected String firstName;

    protected String lastName;

    protected String city;

    protected String street;

    protected String houseNumber;

    protected String phoneNumber;

    @NotNull
    protected String roles = "ROLE_USER";

    @NotNull(message = "Email is required")
    protected String email;

    private String type;

    private String specialization;

    @DateTimeFormat
    private String bornAt;

}