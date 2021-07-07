package pl.psk.clinicmanagement.presentation.requests;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class UserDetailsRequest {
    protected String firstName;

    protected String lastName;

    protected String city;

    protected String street;

    protected String houseNumber;

    protected String phoneNumber;

    @DateTimeFormat
    private String bornAt;
}
