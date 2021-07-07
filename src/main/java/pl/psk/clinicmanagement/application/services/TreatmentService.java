package pl.psk.clinicmanagement.application.services;


import org.springframework.stereotype.Service;
import pl.psk.clinicmanagement.application.factories.TreatmentFactory;
import pl.psk.clinicmanagement.domain.treatments.Treatment;
import pl.psk.clinicmanagement.domain.treatments.TreatmentsRepository;
import pl.psk.clinicmanagement.presentation.requests.TreatmentsRequest;

@Service
public class TreatmentService {
    private final TreatmentFactory treatmentFactory;
    private final TreatmentsRepository treatmentsRepository;

    public TreatmentService(TreatmentFactory treatmentFactory , TreatmentsRepository treatmentsRepository) {
        this.treatmentFactory = treatmentFactory;
        this.treatmentsRepository = treatmentsRepository;
    }

    public Treatment createFromRequest(TreatmentsRequest treatmentsRequest) {
        Treatment treatment = treatmentFactory.create(
                treatmentsRequest.getName(),
                treatmentsRequest.getCost()
        );

        return treatment;
    }

    public Treatment updateFromRequest(Treatment treatment, TreatmentsRequest treatmentsRequest) {

        treatment.setName(treatmentsRequest.getName());
        treatment.setCost(treatmentsRequest.getCost());


        return treatmentsRepository.save(treatment);
    }

}
