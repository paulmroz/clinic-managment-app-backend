package pl.psk.clinicmanagement.application.services;

import org.springframework.stereotype.Service;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.infrastructure.exceptions.DoctorNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;

import java.security.Principal;

@Service
public class DoctorService {
    private final UserRepository userRepository;

    public DoctorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Doctor getLoggedDoctor(Principal principal) throws DoctorNotFoundException, UserNotFoundException {
        User user = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        Doctor doctor = user.getDoctor();

        if (null == doctor) {
            throw new DoctorNotFoundException();
        }

        return doctor;
    }
}