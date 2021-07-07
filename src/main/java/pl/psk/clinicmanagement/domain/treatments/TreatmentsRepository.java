package pl.psk.clinicmanagement.domain.treatments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreatmentsRepository extends JpaRepository<Treatment, Long> {
    Treatment save(Treatment tretatment);
    Optional<Treatment> findByName(String name);
    List<Treatment> findAll();
    Optional<Treatment> findById(Long id);
    void deleteById(Long id);
    Treatment save(Optional<Treatment> tretatment);
}
