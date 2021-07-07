package pl.psk.clinicmanagement.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Nie znaleziono klienta")
public class ClientNotFoundException extends Exception {
}
