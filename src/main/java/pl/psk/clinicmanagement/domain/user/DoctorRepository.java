package pl.psk.clinicmanagement.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor save(Doctor doctor);
    Optional<Doctor> findById(Long doctorId);
    List<Doctor> findAll();
}