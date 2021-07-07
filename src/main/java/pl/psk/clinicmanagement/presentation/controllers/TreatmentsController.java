package pl.psk.clinicmanagement.presentation.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.psk.clinicmanagement.application.services.TreatmentService;
import pl.psk.clinicmanagement.domain.treatments.Treatment;
import pl.psk.clinicmanagement.domain.treatments.TreatmentsRepository;
import pl.psk.clinicmanagement.infrastructure.exceptions.TreatmentsExistsException;
import pl.psk.clinicmanagement.infrastructure.exceptions.TreatmentsNotFoundException;
import pl.psk.clinicmanagement.presentation.requests.TreatmentsRequest;

import java.util.List;

@RestController
@RequestMapping("/treatments")
@RequiredArgsConstructor
public class TreatmentsController {

    final private TreatmentsRepository treatmentsRepository;
    final private TreatmentService treatmentsService;

    @GetMapping("")
    public ResponseEntity<List<Treatment>> getAll() {
        return ResponseEntity.ok(treatmentsRepository.findAll());
    }



//    @PostMapping("")
//    public ResponseEntity<Treatment> save(@RequestBody @Validated TreatmentsRequest treatmentsRequest) {
//
//        return ResponseEntity.ok(treatmentsService.createFromRequest(treatmentsRequest));
//    }

    @PostMapping("")
    public ResponseEntity<Treatment> save(@RequestBody @Validated TreatmentsRequest treatmentsRequest) throws TreatmentsExistsException {
        if (treatmentsRepository.findByName(treatmentsRequest.getName()).isPresent()) {
            throw new TreatmentsExistsException();
        }
        return ResponseEntity.ok(treatmentsService.createFromRequest(treatmentsRequest));
    }
//
    @DeleteMapping("/{treatmentId}")
    public ResponseEntity<Treatment> delete(@PathVariable Long treatmentId) throws TreatmentsNotFoundException {
        treatmentsRepository
                .findById(treatmentId)
                .orElseThrow(TreatmentsNotFoundException::new);

        treatmentsRepository.deleteById(treatmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{treatmentId}")
    public Treatment getOne(@PathVariable Long treatmentId) throws TreatmentsNotFoundException {
        return treatmentsRepository
                .findById(treatmentId)
                .orElseThrow(TreatmentsNotFoundException::new);

    }

    @PutMapping("{treatmentId}")
    public ResponseEntity<Treatment> update(
            @RequestBody @Validated TreatmentsRequest treatmentsRequest,
            @PathVariable Long treatmentId
    ) throws TreatmentsNotFoundException {
        Treatment updatedTreatment = treatmentsRepository.findById(treatmentId)
                .map(treatment -> treatmentsService.updateFromRequest(treatment, treatmentsRequest))
                .orElseThrow(TreatmentsNotFoundException::new);

        return ResponseEntity.ok(updatedTreatment);
    }
}
