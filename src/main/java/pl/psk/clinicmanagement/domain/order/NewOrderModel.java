package pl.psk.clinicmanagement.domain.order;

import lombok.Getter;
import pl.psk.clinicmanagement.domain.operation.Visit;

import java.math.BigDecimal;

@Getter
public class NewOrderModel {
    private Visit visit;
    private BigDecimal value;
}

