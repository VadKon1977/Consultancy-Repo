package com.consultancy.project.controller;

import com.consultancy.project.service.consultant.ConsultantService;
import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.util.Constants;
import com.consultancy.project.util.JsonUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j// Lombok generates: private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ConsultantController.class);
@RequestMapping("/v1/consultant")
@RequiredArgsConstructor

public class ConsultantController {

    private final ConsultantService consultantService;
    private final JsonUtility jsonUtility;

    @PostMapping("/consultants")
    public ResponseEntity<ConsultantDTO> create(@Valid @RequestBody ConsultantDTO dto) {
        log.info("Create consultant controller received request at {} at " + LocalDateTime.now()
                , jsonUtility.writeToJson(dto, Constants.CREATE));
        ConsultantDTO savedDto = consultantService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantDTO> getConsultantById(@PathVariable Long id) {
        log.info("Find consultant by id {} endpoint hit at {}", id, LocalDateTime.now());
        return ResponseEntity.ok(consultantService.findById(id));
    }

    @GetMapping("/get/all")
    public ResponseEntity <List<ConsultantDTO>> getAll() {
        log.info("Get consultant list endpoint have been hit at {}", LocalDateTime.now());
        List<ConsultantDTO> consultants = consultantService.findAll();
        return Optional.ofNullable(consultants).filter(list -> !list.isEmpty()).map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultantDTO> update(@PathVariable Long id, @Valid @RequestBody ConsultantDTO consultantDTO) {
        log.info("Consultant full update endpoint has been hit with consultant id {} and body {} at {}"
                , id, jsonUtility.writeToJson(consultantDTO, Constants.UPDATE), LocalDateTime.now());
        return ResponseEntity.ok(consultantService.update(id, consultantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Delete consultant has been hid with id {} at {}", id, LocalDateTime.now());
        consultantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ConsultantDTO> partialUpdate(@PathVariable Long id, @RequestBody ConsultantDTO patchDto){
        log.info("Partial update endpoint has been hit with id {} and request {} at {}",
                id, jsonUtility.writeToJson(patchDto, Constants.PATCH), LocalDateTime.now());
        return ResponseEntity.ok(consultantService.patch(id, patchDto));
    }
}
