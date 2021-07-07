package pl.psk.clinicmanagement.presentation.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.psk.clinicmanagement.application.payment.NewOrderResponse;
import pl.psk.clinicmanagement.application.payment.services.PaymentService;
import pl.psk.clinicmanagement.domain.operation.Visit;
import pl.psk.clinicmanagement.domain.operation.VisitRepository;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.infrastructure.exceptions.VisitNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.ClientNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;

import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping("/payment")
public class PaymentsController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public PaymentsController(
            VisitRepository visitRepository,
            UserRepository userRepository,
            PaymentService paymentService
    ) {
        this.visitRepository = visitRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    @GetMapping("/{visitId}/pay")
    public ResponseEntity<NewOrderResponse> createPayment(@PathVariable Long visitId, Principal principal) throws ClientNotFoundException, VisitNotFoundException, UserNotFoundException {

        //User user = userRepository.findByEmail(principal.getName()).orElseThrow(UserNotFoundException::new);

        Optional<User> user = null;

        if(userRepository.findByEmail(principal.getName()).isPresent()){
            user = userRepository.findByEmail(principal.getName());
        } else {
            user = userRepository.findByFacebookID(principal.getName().split(":")[1]);
        }

        Client client = user.get().getClient();

        if (null == client) {
            throw new ClientNotFoundException();
        }

        Visit visit = visitRepository.findByIdAndClient(visitId, client)
                .orElseThrow(VisitNotFoundException::new);

        val paymentResponse = paymentService.createPayment(visit);

        visit.setExternalId(paymentResponse.getOrderId());

        visitRepository.save(visit);

        val newOrderResponse =  NewOrderResponse.builder().redirectUrl(paymentResponse.getRedirectUri()).build();

        return ResponseEntity.ok(newOrderResponse);


    }

}