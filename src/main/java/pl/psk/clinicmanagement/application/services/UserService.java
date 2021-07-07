package pl.psk.clinicmanagement.application.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.psk.clinicmanagement.application.factories.DoctorFactory;
import pl.psk.clinicmanagement.application.factories.ClientFactory;
import pl.psk.clinicmanagement.application.factories.UserFactory;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.domain.user.Client;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.domain.user.DoctorRepository;
import pl.psk.clinicmanagement.domain.user.ClientRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.ClientNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.UserDetailsRequest;
import pl.psk.clinicmanagement.presentation.requests.UserEditRequest;
import pl.psk.clinicmanagement.presentation.requests.UserRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserFactory userFactory;
    private final ClientFactory clientFactory;
    private final DoctorFactory doctorFactory;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ClientRepository clientRepository;

    public UserService(
            PasswordEncoder passwordEncoder,
            UserFactory userFactory,
            ClientFactory clientFactory,
            DoctorFactory doctorFactory,
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            ClientRepository clientRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userFactory = userFactory;
        this.clientFactory = clientFactory;
        this.doctorFactory = doctorFactory;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.clientRepository = clientRepository;
    }



    public User updateFromRequest(User user, UserEditRequest userRequest) {
        if (!StringUtils.isEmpty(userRequest.getPassword())) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setCity(userRequest.getCity());
        user.setStreet(userRequest.getStreet());
        user.setHouseNumber(userRequest.getHouseNumber());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRoles(userRequest.getRoles());

        if (null != user.getDoctor()) {
            Doctor doctor = user.getDoctor();
            doctor.setSpecialization(userRequest.getSpecialization());
            doctorRepository.save(doctor);
        }

        if (null != user.getClient()) {
            Client client = user.getClient();
            client.setBornAt(
                    LocalDate.parse(
                            userRequest.getBornAt(),
//                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            DateTimeFormatter.ISO_DATE_TIME
                    )
            );

            clientRepository.save(client);


        }
        return userRepository.save(user);
    }

    public User createFromRequest(UserRequest userRequest) {
        User user = userFactory.create(
                userRequest.getEmail(),
                passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getCity(),
                userRequest.getStreet(),
                userRequest.getHouseNumber(),
                userRequest.getPhoneNumber(),
                userRequest.getRoles()
        );

        if (User.DOCTOR_TYPE.equals(userRequest.getType())) {
            Doctor doctor = doctorFactory.create(userRequest.getSpecialization(), user);

            user.setDoctor(doctor);
        }

        if (User.CLIENT_TYPE.equals(userRequest.getType())) {
            Client client = clientFactory.create(
                    LocalDate.parse(userRequest.getBornAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    user
            );

            user.setClient(client);
        }

        return user;
    }



    public User updatePersonalData(String userEmail, UserDetailsRequest userRequest) throws UserNotFoundException {
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setCity(userRequest.getCity());
        user.setStreet(userRequest.getStreet());
        user.setHouseNumber(userRequest.getHouseNumber());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        return userRepository.save(user);
    }


    public User updatePersonalDataFb(String fbId, UserDetailsRequest userRequest) throws UserNotFoundException, ClientNotFoundException {
        User user = userRepository
                .findByFacebookID(fbId)
                .orElseThrow(UserNotFoundException::new);


        user.setCity(userRequest.getCity());
        user.setStreet(userRequest.getStreet());
        user.setHouseNumber(userRequest.getHouseNumber());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        Client client = clientRepository.findByUser(user).orElseThrow(ClientNotFoundException::new);

        client.setBornAt(LocalDate.parse(
                userRequest.getBornAt(),
//                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                DateTimeFormatter.ISO_DATE_TIME
        ));

        clientRepository.save(client);

        return userRepository.save(user);
    }
}
