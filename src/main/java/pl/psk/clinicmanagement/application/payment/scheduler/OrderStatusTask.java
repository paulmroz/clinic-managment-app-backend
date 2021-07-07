package pl.psk.clinicmanagement.application.payment.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.psk.clinicmanagement.application.payment.services.PaymentService;
import pl.psk.clinicmanagement.application.services.VisitService;
import pl.psk.clinicmanagement.domain.operation.Visit;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class OrderStatusTask {
    private final static String COMPLETED_STATUS = "COMPLETED";

    private final PaymentService paymentService;
    private final VisitService visitService;

    @Scheduled(fixedRate = 9000)
    @Transactional
    public void checkOrdersStatus() {
        val unpaidVisit = visitService.getUnpaidOrders();

        for (Visit visit : unpaidVisit) {
            val payuResponse = paymentService.checkPayment(visit);
            payuResponse.getOrders()
                    .stream()
                    .filter(payuOrder -> payuOrder.getOrderId().equals(visit.getExternalId()) && payuOrder.getStatus().equals(COMPLETED_STATUS))
                    .findFirst()
                    .ifPresent(payuOrder -> visitService.changeOrderStatus(visit, "true"));

        }
    }
}
