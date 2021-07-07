package pl.psk.clinicmanagement.application.services;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class DoctorServiceTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    public void getLoggedDoctorExceptionTest() {
        val principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("TEST");

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.empty());

        val doctorFacade = new DoctorService(userRepository);

        assertThatThrownBy(() -> {
            doctorFacade.getLoggedDoctor(principal);
        }).isInstanceOf(UserNotFoundException.class);
    }
}
