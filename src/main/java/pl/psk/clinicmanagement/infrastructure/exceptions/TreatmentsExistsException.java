package pl.psk.clinicmanagement.infrastructure.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Usługa o podanej nazwie juz istniejeee")
public class TreatmentsExistsException extends Exception{
}
