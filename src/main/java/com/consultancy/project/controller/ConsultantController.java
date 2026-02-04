package com.consultancy.project.controller;

import com.consultancy.project.exceptions.DatabaseErrorException;
import com.consultancy.project.service.consultant.ConsultantService;
import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.util.Constants;
import com.consultancy.project.util.ConsultantMapper;
import com.consultancy.project.util.JsonUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
    private final ConsultantMapper consultantMapper;

    @PostMapping("/consultants")
    public ResponseEntity<ConsultantDTO> create(@Valid @RequestBody ConsultantDTO dto) {
        log.info("Create consultant controller received request at {}"
                , jsonUtility.writeToJson(dto, Constants.CREATE));
        ConsultantDTO savedDto = consultantService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantDTO> getConsultantById(@PathVariable Long id) {
        ConsultantDTO dto;
        log.info("Find consultant by id {} endpoint hit", id);
        try {
            dto = consultantMapper.toDto(consultantService.findById(id));
        } catch (Exception ex){
            log.error("[TracingId:{}] Data Transformation Error for Id {} at {}", MDC.get(Constants.TRACE_ID_KEY), id, MDC.get(Constants.REQUEST_TIME));
            throw new DatabaseErrorException("Data Transformation Error", "MAPPER ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.info("[TracingId:{}] Consultant Successfully Retrieved by Id {} at {}", MDC.get(Constants.TRACE_ID_KEY), id, MDC.get(Constants.REQUEST_TIME));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get/all")
    public ResponseEntity <List<ConsultantDTO>> getAll() {
        log.info("Get consultant list endpoint have been hit");
        List<ConsultantDTO> consultants = consultantService.findAll();
        return Optional.ofNullable(consultants).filter(list -> !list.isEmpty()).map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultantDTO> update(@PathVariable Long id, @Valid @RequestBody ConsultantDTO consultantDTO) {
        log.info("Consultant full update endpoint has been hit with consultant id {} and body {}"
                , id, jsonUtility.writeToJson(consultantDTO, Constants.UPDATE));
        log.info("[TracingId:{}] Consultant Data is Successfully Updated Id {} at {}", MDC.get(Constants.TRACE_ID_KEY), id, MDC.get(Constants.REQUEST_TIME));
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
