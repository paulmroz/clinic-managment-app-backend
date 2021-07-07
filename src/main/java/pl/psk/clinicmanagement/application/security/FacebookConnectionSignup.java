package pl.psk.clinicmanagement.application.security;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.ClientRepository;

@Service
@RequiredArgsConstructor
public class FacebookConnectionSignup implements ConnectionSignUp {

    private final UserRepository userRepository;
    private final FBService fbService;
    private final ClientRepository clientRepository;

    @Override
    public String execute(Connection<?> connection) {
        val user =  fbService.getProfile("me", connection.createData().getAccessToken());

        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {
            User userSaved = userRepository.save(
                    User.builder()
                            .email(user.getEmail())
                            .facebookID(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .roles("ROLE_USER,ROLE_CLIENT,FB_USER")
                            .build()
            );

            Client client = new Client();
            client.setUser(userSaved);
            clientRepository.save(client);
        }

        return connection.getDisplayName();
    }
}
