package pl.psk.clinicmanagement.application.payment.services;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class NewPayment {
    private String notifyUrl;
    private String customerIp;
    private String merchantPosId;
    private String description;
    private String currencyCode;
    private String totalAmount;
    private String extOrderId;
    private NewPaymentBuyer buyer;
    private List<NewPaymentItem> products;
    private String continueUrl;
}
