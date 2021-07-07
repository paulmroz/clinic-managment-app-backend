package pl.psk.clinicmanagement.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.psk.clinicmanagement.application.services.UserService;
import pl.psk.clinicmanagement.domain.security.User;
import pl.psk.clinicmanagement.domain.security.UserRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserExistsException;
import pl.psk.clinicmanagement.infrastructure.exceptions.UserNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.UserEditRequest;
import pl.psk.clinicmanagement.presentation.requests.UserRequest;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    final private UserRepository userRepository;
    final private UserService userService;

    public UserController(
            UserRepository userRepository,
            UserService userService
    ) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("{userId}")
    public User getOne(@PathVariable Long userId) throws UserNotFoundException {
        return userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);

    }

    @PostMapping("")
    public ResponseEntity<User> save(@RequestBody @Validated UserRequest userRequest) throws UserExistsException {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        return ResponseEntity.ok(userService.createFromRequest(userRequest));
    }

    @PutMapping("{userId}")
    public ResponseEntity<User> update(
            @RequestBody @Validated UserEditRequest userEditRequest,
            @PathVariable Long userId
    ) throws UserNotFoundException {
        User updatedUser = userRepository.findById(userId)
                .map(user -> userService.updateFromRequest(user, userEditRequest))
                .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) throws UserNotFoundException {
        userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);

        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
