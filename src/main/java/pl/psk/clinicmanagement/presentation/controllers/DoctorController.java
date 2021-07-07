package pl.psk.clinicmanagement.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.psk.clinicmanagement.domain.user.Doctor;
import pl.psk.clinicmanagement.domain.user.DoctorRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.DoctorNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Doctor>> getAll() {
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    @GetMapping("{doctorId}")
    public Doctor getOne(@PathVariable long doctorId) throws DoctorNotFoundException {
        return doctorRepository
                .findById(doctorId)
                .orElseThrow(DoctorNotFoundException::new);
    }
}
