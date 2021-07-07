package pl.psk.clinicmanagement.application.payment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentGatewayAuth {
    private final String accessToken;
    private final String expiresIn;
}

