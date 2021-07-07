package pl.psk.clinicmanagement.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.psk.clinicmanagement.domain.security.User;

import java.util.List;
import java.util.Optional;
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client save(Client client);
    Optional<Client> findById(Long patientId);
    List<Client> findAll();
    Optional<Client> findByUser(User user);
}
