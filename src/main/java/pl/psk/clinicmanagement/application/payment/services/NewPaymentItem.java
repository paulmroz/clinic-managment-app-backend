package pl.psk.clinicmanagement.application.payment.services;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewPaymentItem {
    private String name;
    private String unitPrice;
    private int quantity;
}
