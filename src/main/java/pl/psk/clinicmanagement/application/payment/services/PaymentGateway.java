package pl.psk.clinicmanagement.application.payment.services;

import pl.psk.clinicmanagement.application.payment.domain.PaymentGatewayAuth;
import pl.psk.clinicmanagement.domain.operation.Visit;

public interface PaymentGateway {
    public PaymentGatewayAuth authenticate();
    public PayuPaymentResponse createPayment(Visit visit, String accessToken);
    public PayuCheckPaymentResponse checkPayment(Visit visit, String accessToken);
}

