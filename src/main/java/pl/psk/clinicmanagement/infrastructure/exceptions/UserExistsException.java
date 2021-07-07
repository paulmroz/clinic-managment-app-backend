package pl.psk.clinicmanagement.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Użytkownik o podanym adresie email juz istnieje")
public class UserExistsException extends Exception {
}
