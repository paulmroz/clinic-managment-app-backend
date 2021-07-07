package pl.psk.clinicmanagement.application.factories;

import org.springframework.stereotype.Component;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;

@Component
public class UserFactory {
    private final UserRepository userRepository;

    public UserFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(
            String email,
            String password,
            String firstName,
            String lastName,
            String city,
            String street,
            String houseNumber,
            String phoneNumber,
            String roles
    ) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCity(city);
        user.setStreet(street);
        user.setHouseNumber(houseNumber);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
