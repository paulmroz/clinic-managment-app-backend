package pl.psk.clinicmanagement.application.factories;

import org.springframework.stereotype.Component;
import pl.psk.clinicmanagement.domain.treatments.Treatment;
import pl.psk.clinicmanagement.domain.treatments.TreatmentsRepository;

@Component
public class TreatmentFactory {

    private final TreatmentsRepository treatmentsRepository;

    public TreatmentFactory(TreatmentsRepository treatmentsRepository) {
        this.treatmentsRepository = treatmentsRepository;
    }

    public Treatment create(String name, String cost) {
        Treatment treatment = new Treatment();

        treatment.setName(name);
        treatment.setCost(cost);

        return treatmentsRepository.save(treatment);
    }
}