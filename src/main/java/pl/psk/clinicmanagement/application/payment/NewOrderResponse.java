package pl.psk.clinicmanagement.application.payment;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewOrderResponse {
    private final String redirectUrl;
}
