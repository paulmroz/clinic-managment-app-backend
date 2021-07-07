package pl.psk.clinicmanagement.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.psk.clinicmanagement.application.services.DoctorService;
import pl.psk.clinicmanagement.application.services.VisitService;
import pl.psk.clinicmanagement.domain.operation.Visit;
import pl.psk.clinicmanagement.domain.operation.VisitRepository;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.infrastructure.exceptions.DoctorNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.VisitDateBlocked;
import pl.psk.clinicmanagement.infrastructure.exceptions.VisitNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.AddVisitDetailsRequest;
import pl.psk.clinicmanagement.presentation.requests.ChangeVisitDateRequest;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/doctor/visits")
public class DoctorVisitController {
    private final VisitRepository visitRepository;
    private final DoctorService doctorService;
    private final VisitService visitService;

    public DoctorVisitController(
            VisitRepository visitRepository,
            DoctorService doctorService,
            VisitService visitService
            ) {
        this.visitRepository = visitRepository;
        this.doctorService = doctorService;
        this.visitService = visitService;
    }

    @GetMapping("")
    public ResponseEntity<List<Visit>> getAll(
            Principal principal,
            @RequestParam(required = false, defaultValue = "empty") String type
    ) throws UserNotFoundException, DoctorNotFoundException {
        Doctor doctor = doctorService.getLoggedDoctor(principal);

        List<Visit> visits;

        switch (type) {
            case VisitController.ACTUAL_TYPE:
                visits = visitRepository.findAllByDoctorAndScheduledForAfter(doctor, LocalDateTime.now());
                break;
            case VisitController.ARCHIVED_TYPE:
                visits = visitRepository.findAllByDoctorAndScheduledForBefore(doctor, LocalDateTime.now());
                break;
            default:
                visits = doctor.getVisits();
                break;
        }
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/{visitId}")
    public ResponseEntity<Visit> getOne(@PathVariable Long visitId, Principal principal) throws UserNotFoundException, DoctorNotFoundException, VisitNotFoundException {
        Doctor doctor = doctorService.getLoggedDoctor(principal);

        Visit visit = visitRepository.findByDoctorAndId(doctor, visitId)
                .orElseThrow(VisitNotFoundException::new);

        return ResponseEntity.ok(visit);
    }

    @PutMapping("/{visitId}/add-details")
    public ResponseEntity<Visit> addDetails(
            @PathVariable Long visitId,
            @RequestBody AddVisitDetailsRequest addVisitDetailsRequest,
            Principal principal
    ) throws UserNotFoundException, DoctorNotFoundException, VisitNotFoundException {
        Doctor doctor = doctorService.getLoggedDoctor(principal);

        Visit visit = visitService.fulfillOperationDetails(
                doctor,
                visitId,
                addVisitDetailsRequest.getDetails()
        );
        return ResponseEntity.ok(visit);
    }

    @PutMapping("/{visitId}/change-date")
    public ResponseEntity<Visit> changeDate(
            @PathVariable Long visitId,
            @RequestBody ChangeVisitDateRequest changeOperationDateRequest,
            Principal principal
    ) throws UserNotFoundException, DoctorNotFoundException, VisitNotFoundException, VisitDateBlocked {
        //Doctor doctor = doctorService.getLoggedDoctor(principal);

        Visit visit = visitService.changeOperationDate(
                visitId,
                LocalDateTime.parse(
                        changeOperationDateRequest.getScheduledFor(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                )
        );
        return ResponseEntity.ok(visit);
    }
}

