package pl.psk.clinicmanagement.presentation.controllers;

import com.itextpdf.text.DocumentException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.psk.clinicmanagement.application.services.VisitService;
import pl.psk.clinicmanagement.domain.operation.Visit;
import pl.psk.clinicmanagement.domain.operation.VisitRepository;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.ClientRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.*;
import pl.psk.clinicmanagement.presentation.requests.VisitRequest;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitController {
    public final static String ARCHIVED_TYPE = "archived";
    public final static String ACTUAL_TYPE = "actual";

    private final VisitRepository visitRepository;
    private final ClientRepository clientRepository;
    private final VisitService visitService;

    @Autowired
    public VisitController(
            VisitRepository visitRepository,
            ClientRepository clientRepository,
            UserRepository userRepository,
            VisitService visitService
    ) {
        this.visitRepository = visitRepository;
        this.clientRepository = clientRepository;
        this.visitService = visitService;
    }

    @GetMapping
    public ResponseEntity<List<Visit>> getAll() {
        return ResponseEntity.ok(visitRepository
                .findAll());

    }

    @GetMapping("/{visitId}")
    public Visit getOne(@PathVariable Long visitId) throws VisitNotFoundException {
        return visitRepository
                .findById(visitId)
                .orElseThrow(VisitNotFoundException::new);
    }

    @PostMapping("")
    public ResponseEntity<Visit> save(
            @RequestBody @Validated VisitRequest visitRequest
    ) throws ClientNotFoundException, DoctorNotFoundException, VisitDateBlocked {
        return ResponseEntity.ok(visitService.createFromRequest(visitRequest));
    }

    @GetMapping("/client/{visitId}")
    public ResponseEntity<List<Visit>> clientVisits(@PathVariable Long visitId) throws ClientNotFoundException {
        Client client = clientRepository.findById(visitId).orElseThrow(ClientNotFoundException::new);

        return ResponseEntity.ok(client.getVisits());
    }

}

