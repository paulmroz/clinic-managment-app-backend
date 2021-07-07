package pl.psk.clinicmanagement.domain.operation;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.domain.user.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByDoctorAndId(Doctor doctor, Long id);
    Optional<Visit> findByScheduledForAndIdIsNot(LocalDateTime scheduledFor, Long id);
    Optional<Visit> findByScheduledFor(LocalDateTime scheduledFor);
    List<Visit> findAllByDoctorAndScheduledForAfter(Doctor doctor, LocalDateTime schedulerFor);
    List<Visit> findAllByDoctorAndScheduledForBefore(Doctor doctor, LocalDateTime schedulerFor);
    List<Visit> findAllByClientAndScheduledForAfter(Client client, LocalDateTime schedulerFor);
    List<Visit> findAllByClientAndScheduledForBefore(Client client, LocalDateTime schedulerFor);
    Visit save(Visit visit);
    List<Visit> findAll();
    Optional<Visit> findById(Long id);
    Optional<Visit> findByIdAndClient(Long id, Client client);
    List<Visit> findAllByIsPayed(String status);
    List<Visit> findAllByScheduledForBetween(LocalDateTime startDate, LocalDateTime endDate);

}
