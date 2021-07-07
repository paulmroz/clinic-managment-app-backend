package pl.psk.clinicmanagement.application.payment.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import pl.psk.clinicmanagement.domain.operation.Visit;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentGateway paymentGateway;

    public PayuPaymentResponse createPayment(Visit visit) {
        val authenticateResponse = paymentGateway.authenticate();
        return paymentGateway.createPayment(visit, authenticateResponse.getAccessToken());
    }

    public PayuCheckPaymentResponse checkPayment(Visit visit) {
        val authenticateResponse = paymentGateway.authenticate();
        return paymentGateway.checkPayment(visit, authenticateResponse.getAccessToken());
    }
}
