package pl.psk.clinicmanagement.application.payment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PayuGatewayConfiguration {
    @Value("${bs.payment.payu.url}")
    private String url;
    @Value("${bs.payment.payu.clientId}")
    private String clientId;
    @Value("${bs.payment.payu.clientSecret}")
    private String clientSecret;
    @Value("${bs.payment.payu.posId}")
    private String posId;
    @Value("${bs.payment.payu.storeName}")
    private String storeName;
    @Value("${bs.payment.payu.currency}")
    private String currency;
    @Value("${bs.payment.payu.notifyUrl}")
    private String notifyUrl;
    @Value("${bs.payment.payu.continueUrl}")
    private String continueUrl;
}

