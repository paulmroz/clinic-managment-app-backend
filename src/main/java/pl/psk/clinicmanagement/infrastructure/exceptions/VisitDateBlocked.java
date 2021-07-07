package pl.psk.clinicmanagement.infrastructure.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Termin wizyty jest juz zarezerwowany")
public class VisitDateBlocked extends Exception {
}