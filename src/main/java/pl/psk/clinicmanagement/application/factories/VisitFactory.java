package pl.psk.clinicmanagement.application.factories;

import org.springframework.stereotype.Component;
import pl.psk.clinicmanagement.domain.operation.Visit;
import pl.psk.clinicmanagement.domain.operation.VisitRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.Doctor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class VisitFactory {
    private final VisitRepository visitRepository;

    public VisitFactory(VisitRepository visitRepository) {

        this.visitRepository = visitRepository;
    }

    public Visit create(String name, BigDecimal price, String isPayed, String externalId, Doctor doctor, Client client, LocalDateTime date) {
        Visit visit = new Visit();
        visit.setName(name);
        visit.setIsPayed(isPayed);
        visit.setPrice(price);
        visit.setDoctor(doctor);
        visit.setClient(client);
        visit.setScheduledFor(date);
        visit.setExternalId(externalId);

        return visitRepository.save(visit);
    }
}
