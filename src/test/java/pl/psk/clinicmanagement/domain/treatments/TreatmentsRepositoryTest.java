package pl.psk.clinicmanagement.domain.treatments;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TreatmentsRepositoryTest {

    @Autowired
    private TreatmentsRepository treatmentsRepository;

    @Test
    public void findTreatatmentTest() {
        assertThat(treatmentsRepository.findById(2L).isPresent()).isTrue();
    }

    @Test
    public void deleteTreatatmentTest() {
        treatmentsRepository.deleteById(2L);

        assertThat(treatmentsRepository.findById(2L).isPresent()).isFalse();
    }

    @Test
    public void saveNewTreatatment() {
        val treatment= treatmentsRepository.save(Treatment.builder().cost("20").name("TEST").build());

        assertThat(treatmentsRepository.findById(treatment.getId()).isPresent()).isTrue();
    }
}
