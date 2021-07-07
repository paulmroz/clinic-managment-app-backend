package pl.psk.clinicmanagement.application.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.psk.clinicmanagement.application.factories.ClientFactory;
import pl.psk.clinicmanagement.application.factories.UserFactory;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.infrastructure.exceptions.ClientNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.ClientRequest;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ClientService {
    private final UserFactory userFactory;
    private final ClientFactory clientFactory;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ClientService(
            UserFactory userFactory,
            ClientFactory clientFactory,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository) {
        this.userFactory = userFactory;
        this.clientFactory = clientFactory;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Client getLoggedClient(Principal principal) throws ClientNotFoundException, UserNotFoundException {
        User user = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(UserNotFoundException::new);

        Client client = user.getClient();

        if (null == client) {
            throw new ClientNotFoundException();
        }

        return client;
    }

    public Client createFromRequest(ClientRequest clientRequest) {
        User user = userFactory.create(
                clientRequest.getEmail(),
                passwordEncoder.encode(clientRequest.getPassword()),
                clientRequest.getFirstName(),
                clientRequest.getLastName(),
                clientRequest.getCity(),
                clientRequest.getStreet(),
                clientRequest.getHouseNumber(),
                clientRequest.getPhoneNumber(),
                "ROLE_USER,ROLE_CLIENT"
        );


        return clientFactory.create(
                LocalDate.parse(clientRequest.getBornAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                user
        );
    }
}
