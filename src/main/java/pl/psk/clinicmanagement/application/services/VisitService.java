package pl.psk.clinicmanagement.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.psk.clinicmanagement.application.factories.VisitFactory;
import pl.psk.clinicmanagement.domain.operation.Visit;
import pl.psk.clinicmanagement.domain.operation.VisitRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.domain.user.DoctorRepository;
import pl.psk.clinicmanagement.domain.user.ClientRepository;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.psk.clinicmanagement.infrastructure.exceptions.DoctorNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.VisitDateBlocked;
import pl.psk.clinicmanagement.infrastructure.exceptions.VisitNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.ClientNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.VisitRequest;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final ClientRepository clientRepository;
    private final DoctorRepository doctorRepository;
    private final VisitFactory visitFactory;

    @Autowired
    public VisitService(
            VisitRepository visitRepository,
            ClientRepository clientRepository,
            DoctorRepository doctorRepository,
            VisitFactory visitFactory
    ) {
        this.visitRepository = visitRepository;
        this.clientRepository = clientRepository;
        this.doctorRepository = doctorRepository;
        this.visitFactory = visitFactory;
    }

    public Visit changeOperationDate(
            Long operationId,
            LocalDateTime newDate
    ) throws VisitDateBlocked, VisitNotFoundException {
        Visit visit = visitRepository.findById(operationId)
                .orElseThrow(VisitNotFoundException::new);

        Optional<Visit> existingVisit = visitRepository
                .findByScheduledForAndIdIsNot(newDate, visit.getId());

        if (existingVisit.isPresent()) {
            throw new VisitDateBlocked();
        }

        visit.setScheduledFor(newDate);

        return visitRepository.save(visit);
    }

    public Visit fulfillOperationDetails(
            Doctor doctor,
            Long operationId,
            String details
    ) throws VisitNotFoundException {
        Visit visit = visitRepository.findByDoctorAndId(doctor, operationId)
                .orElseThrow(VisitNotFoundException::new);

        visit.setDetails(details);

        return visitRepository.save(visit);
    }

    public Visit createFromRequest(
            VisitRequest visitRequest
    ) throws VisitDateBlocked, DoctorNotFoundException, ClientNotFoundException {
        Client client = clientRepository.findById(visitRequest.getClientId())
                .orElseThrow(ClientNotFoundException::new);

        Doctor doctor = doctorRepository.findById(visitRequest.getDoctorId())
                .orElseThrow(DoctorNotFoundException::new);

        LocalDateTime date = LocalDateTime.parse(
                visitRequest.getScheduledFor(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );

        Optional<Visit> existingVisit = visitRepository
                .findByScheduledFor(date);

        if (existingVisit.isPresent()) {
            throw new VisitDateBlocked();
        }

        Visit visit = visitFactory.create(visitRequest.getName(), visitRequest.getPrice(), visitRequest.getIsPayed(), visitRequest.getExternalId(), doctor, client, date);


        return visit;
    }


    public List<Visit> getUnpaidOrders() {
        return visitRepository.findAllByIsPayed("false");
    }

    public void changeOrderStatus(Visit visit, String status) {
        visit.setIsPayed(status);
        visitRepository.save(visit);
    }
}
