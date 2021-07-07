package pl.psk.clinicmanagement.application.factories;

import org.springframework.stereotype.Component;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.ClientRepository;

import java.time.LocalDate;

@Component
public class ClientFactory {
    private final ClientRepository clientRepository;

    public ClientFactory(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client create(LocalDate bornAt, User user) {
        Client client = new Client();

        client.setBornAt(bornAt);
        client.setUser(user);

        return clientRepository.save(client);
    }
}
