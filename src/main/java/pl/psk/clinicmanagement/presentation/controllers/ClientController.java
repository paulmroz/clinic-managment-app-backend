package pl.psk.clinicmanagement.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.psk.clinicmanagement.application.services.ClientService;
import pl.psk.clinicmanagement.domain.operation.Visit;
import pl.psk.clinicmanagement.domain.operation.VisitRepository;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.ClientRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.ClientNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserExistsException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.ClientRequest;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final ClientService clientService;

    public ClientController(
            ClientRepository clientRepository,
            UserRepository userRepository,
            VisitRepository visitRepository,
            ClientService clientService
    ) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
        this.clientService = clientService;
    }

    @GetMapping("")
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientRepository.findAll());
    }

    @GetMapping("/{clientId}")
    public Client getOne(@PathVariable Long clientId) throws ClientNotFoundException {
        return clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);
    }

    @GetMapping("/{clientId}/operations")
    public ResponseEntity<List<Visit>> clientVisits(
            @PathVariable Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);

        return ResponseEntity.ok(client.getVisits());
    }

    @PostMapping("")
    public ResponseEntity<Client> save(@RequestBody @Validated ClientRequest clientRequest) throws UserExistsException {
        if (userRepository.findByEmail(clientRequest.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        return ResponseEntity.ok(clientService.createFromRequest(clientRequest));
    }

//    @PostMapping("")
//    public ResponseEntity<Patient> save(@RequestBody @Validated PatientRequest patientRequest) {
//        return ResponseEntity.ok(patientService.createFromRequest(patientRequest));
//    }

    @GetMapping("visits")
    public ResponseEntity<List<Visit>> clientVisits(
            Principal principal,
            @RequestParam(required = false, defaultValue = "empty") String type
    ) throws UserNotFoundException, ClientNotFoundException {

        Optional<User> user = null;

        if(userRepository.findByEmail(principal.getName()).isPresent()){
            user = userRepository.findByEmail(principal.getName());
        } else {
            user = userRepository.findByFacebookID(principal.getName().split(":")[1]);
        }
        //User user = userRepository.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        if (null == user.get().getClient()) {
            throw new ClientNotFoundException();
        }

        List<Visit> visits;

        switch (type) {
            case VisitController.ACTUAL_TYPE:
                visits = visitRepository
                        .findAllByClientAndScheduledForAfter(user.get().getClient(), LocalDateTime.now());
                break;
            case VisitController.ARCHIVED_TYPE:
                visits = visitRepository
                        .findAllByClientAndScheduledForBefore(user.get().getClient(), LocalDateTime.now());
                break;
            default:
                visits = user.get().getClient().getVisits();
                break;
        }

        return ResponseEntity.ok(visits);
    }
}
