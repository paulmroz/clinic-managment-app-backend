package pl.psk.clinicmanagement.application.services;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.psk.clinicmanagement.application.factories.ClientFactory;
import pl.psk.clinicmanagement.application.factories.UserFactory;
import pl.psk.clinicmanagement.domain.treatments.Treatment;
import pl.psk.clinicmanagement.domain.treatments.TreatmentsRepository;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.presentation.requests.ClientRequest;

import java.util.Optional;

import static org.mockito.Mockito.atLeast;


@SpringBootTest
public class ClientServiceTest {

    private UserFactory userFactoryMock;
    private ClientFactory clientFactoryMock;
    private PasswordEncoder passwordEncoderMock;
    private TreatmentsRepository treatmentsRepositoryMock;
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setUp() {
        userFactoryMock = Mockito.mock(UserFactory.class);
        clientFactoryMock = Mockito.mock(ClientFactory.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        treatmentsRepositoryMock = Mockito.mock(TreatmentsRepository.class);
        userRepositoryMock = Mockito.mock(UserRepository.class);
    }

    @Test
    public void createClientTest() {
        val patientService = new ClientService(userFactoryMock, clientFactoryMock, passwordEncoderMock,userRepositoryMock);

        val request = new ClientRequest();
        request.setEmail("test@example.org");
        request.setType("client");
        request.setSpecialization("test");
        request.setBornAt("1998-01-01");
        request.setPassword("test");
        request.setPasswordConfirmation("test");

        patientService.createFromRequest(request);

        Mockito.verify(clientFactoryMock, atLeast(1)).create(Mockito.any(),Mockito.any());
    }
}
