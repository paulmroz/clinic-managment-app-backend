package pl.psk.clinicmanagement.presentation.requests;

import lombok.Data;
import pl.psk.clinicmanagement.infrastructure.annotations.FieldMatch;

import javax.validation.constraints.NotNull;

@Data
@FieldMatch(first = "password", second = "passwordConfirmation", message = "The password fields must match")
public class UserBaseRequest {
    @NotNull
    protected String password;

    @NotNull
    protected String passwordConfirmation;

    protected String firstName;

    protected String lastName;

    protected String city;

    protected String street;

    protected String houseNumber;

    protected String phoneNumber;

    @NotNull
    protected String roles = "ROLE_USER";
}
