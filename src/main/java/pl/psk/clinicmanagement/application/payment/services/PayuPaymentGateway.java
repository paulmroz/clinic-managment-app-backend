package pl.psk.clinicmanagement.application.payment.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.psk.clinicmanagement.application.payment.config.PayuGatewayConfiguration;
import pl.psk.clinicmanagement.application.payment.domain.PaymentGatewayAuth;
import pl.psk.clinicmanagement.domain.operation.Visit;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@ConstructorBinding
public class PayuPaymentGateway implements PaymentGateway {
    private final static String ORDER_API_URI = "/api/v2_1/orders";
    private final static String OAUTH_URI = "/pl/standard/user/oauth/authorize";

    private final PayuGatewayConfiguration payuGatewayConfiguration;
    private final RestTemplate restTemplate = new RestTemplate();;

    @Override
    public PaymentGatewayAuth authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "client_credentials");
        parameters.add("client_id", payuGatewayConfiguration.getClientId());
        parameters.add("client_secret", payuGatewayConfiguration.getClientSecret());

        PayuAuthResponse response = restTemplate.postForEntity(
                makeUrl(OAUTH_URI),
                new HttpEntity<>(parameters, headers),
                PayuAuthResponse.class
        ).getBody();

        return Optional.ofNullable(response)
                .map(payuResponse -> PaymentGatewayAuth.builder()
                        .accessToken(payuResponse.getAccess_token())
                        .expiresIn(payuResponse.getExpires_in()).build()
                )
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public PayuPaymentResponse createPayment(Visit order, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        return restTemplate.postForEntity(
                makeUrl(ORDER_API_URI),
                new HttpEntity<>(createPaymentRequest(order), headers),
                PayuPaymentResponse.class
        ).getBody();
    }

    @Override
    public PayuCheckPaymentResponse checkPayment(Visit visit, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        return restTemplate.exchange(
                makeUrl(ORDER_API_URI + "/" + visit.getExternalId()),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                PayuCheckPaymentResponse.class
        ).getBody();
    }

    private NewPayment createPaymentRequest(Visit order) {

        val clientBuyer = order.getClient().getUser();

        val buyer = NewPaymentBuyer.builder()
                .email(clientBuyer.getEmail())
                .firstName(clientBuyer.getFirstName())
                .lastName(clientBuyer.getLastName())
                .phone(clientBuyer.getPhoneNumber())
                .language("en")
                .build();

        val item = NewPaymentItem.builder()
                .name(order.getName())
                .quantity(1)
                .unitPrice("20000")
                .build();


        return NewPayment
                .builder()
                .buyer(buyer)
                .products(Collections.singletonList(item))
                .notifyUrl(payuGatewayConfiguration.getNotifyUrl())
                .customerIp("192.168.2.42")
                .extOrderId(order.getId().toString())
                .merchantPosId(payuGatewayConfiguration.getPosId())
                .description(payuGatewayConfiguration.getStoreName())
                .currencyCode(payuGatewayConfiguration.getCurrency())
                .totalAmount(order.getPrice().multiply(BigDecimal.valueOf(100)).toBigInteger().toString())
                .continueUrl(payuGatewayConfiguration.getContinueUrl())
                .build();
    }

    private String makeUrl(String uri) {
        return String.format("%s%s", payuGatewayConfiguration.getUrl(), uri);
    }
}

