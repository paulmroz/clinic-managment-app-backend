package pl.psk.clinicmanagement.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.psk.clinicmanagement.application.services.UserService;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.ClientNotFoundException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.UserDetailsRequest;


import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;

@RestController
public class AuthorizationController {
    final private UserService userService;
    final private UserRepository userRepository;

    AuthorizationController(
            UserService userService,
            UserRepository userRepository
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/post-signin")
    public void postFacebookSignIn(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "http://localhost:8080/dashboard/profile");
        httpServletResponse.setStatus(302);
    }

    @RequestMapping("/user")
    public Optional<User> user(Principal principal) {
        Optional<User> user = null;
        if(userRepository.findByEmail(principal.getName()).isPresent()){
            user = userRepository.findByEmail(principal.getName());
        } else {
            user = userRepository.findByFacebookID(principal.getName().split(":")[1]);
        }

        return user;
    }

    @GetMapping("/user/current")
    public Optional<User> current(Principal principal) throws UserNotFoundException {
        Optional<User> user = null;

        if(userRepository.findByEmail(principal.getName()).isPresent()){
            user = userRepository.findByEmail(principal.getName());
        } else {
            user = userRepository.findByFacebookID(principal.getName().split(":")[1]);
        }

        return user;
    }

    @PutMapping("/user/change")
    public ResponseEntity<User> changeData(
            @Validated @RequestBody UserDetailsRequest userRequest,
            Principal principal
    ) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updatePersonalData(principal.getName(), userRequest));
    }


    @PutMapping("/user/change/fb")
    public ResponseEntity<User> changeDataFb(
            @Validated @RequestBody UserDetailsRequest userRequest,
            Principal principal
    ) throws UserNotFoundException, ClientNotFoundException {
        return ResponseEntity.ok(userService.updatePersonalDataFb(principal.getName().split(":")[1], userRequest));
    }

}

