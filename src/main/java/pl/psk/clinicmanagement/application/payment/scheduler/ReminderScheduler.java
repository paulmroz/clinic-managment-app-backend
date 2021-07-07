package pl.psk.clinicmanagement.application.payment.scheduler;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.psk.clinicmanagement.application.services.MailService;
import pl.psk.clinicmanagement.domain.operation.Visit;
import pl.psk.clinicmanagement.domain.operation.VisitRepository;
import pl.psk.clinicmanagement.domain.security.User;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class ReminderScheduler {

    private final VisitRepository visitRepository;
    private final MailService mailService;

    @Autowired
    public ReminderScheduler(VisitRepository visitRepository, MailService mailService) {
        this.visitRepository = visitRepository;
        this.mailService = mailService;
    }


    @Scheduled(fixedRate = 9000)
    @Transactional
    public void sendEmailNotification(){
        Date dt = new Date();
        LocalDateTime dateTommorow = LocalDateTime.from(dt.toInstant().atZone(ZoneId.of("UTC"))).plusDays(2);
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        val operations = visitRepository.findAllByScheduledForBetween(localDateTimeNow, dateTommorow);

        for (Visit visit : operations) {
           User visitOwner = visit.getClient().getUser();
            try {
                mailService.sendMail(
                        visitOwner.getEmail(),
                        "Przypomnienie o jutrzejszej wizycie",
                        String.format("Masz jutro wizyte weterynaryjną zaplanowaną na %s", visit.getScheduledFor().toString()),
                        false
                );
            } catch (MessagingException e) {
                Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);
                logger.error(
                        String.format("Nie udalo sie wyslac wiadomosc. Blad \"%s\"", e.toString())
                );
            }


        }



    }
}
