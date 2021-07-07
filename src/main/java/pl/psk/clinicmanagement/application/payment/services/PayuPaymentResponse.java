package pl.psk.clinicmanagement.application.payment.services;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PayuPaymentResponse {
    private String redirectUri;
    private String orderId;
}

