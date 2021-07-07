package pl.psk.clinicmanagement.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Podany doktor nie istnieje")
public class DoctorNotFoundException extends Exception {
}
