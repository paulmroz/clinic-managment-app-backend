package pl.psk.clinicmanagement.application.factories;

import org.springframework.stereotype.Component;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.domain.user.DoctorRepository;

@Component
public class DoctorFactory {
    private final DoctorRepository doctorRepository;

    public DoctorFactory(DoctorRepository doctorRepository) {

        this.doctorRepository = doctorRepository;
    }

    public Doctor create(String specialization, User user) {
        Doctor doctor = new Doctor();

        doctor.setSpecialization(specialization);
        doctor.setUser(user);

        return doctorRepository.save(doctor);
    }
}
