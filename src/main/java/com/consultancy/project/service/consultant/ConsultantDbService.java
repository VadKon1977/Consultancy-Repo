package com.consultancy.project.service.consultant;

import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.repository.ConsultantRepository;
import com.consultancy.project.util.Constants;
import com.consultancy.project.util.JsonUtility;
import com.consultancy.project.util.MappingUtility;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ConsultantDbService {

    private final ConsultantRepository consultantRepository;
    private final JsonUtility jsonUtility;

    private final MappingUtility mappingUtility;

    public ConsultantDbService(ConsultantRepository consultantRepository, JsonUtility jsonUtility, MappingUtility mappingUtility) {
        this.consultantRepository = consultantRepository;
        this.jsonUtility = jsonUtility;
        this.mappingUtility = mappingUtility;
    }

    @Transactional
    public ConsultantDTO save(ConsultantDTO dto) {
        ConsultantEntity saved;
        log.info("Saving New Consultant Data {} at {}",jsonUtility.writeToJson(dto, Constants.SAVE_CONSULTANT) , LocalDateTime.now());
        validateEmailAndPhoneRecordsExistInDatabase(dto);
        try {
            saved = consultantRepository.save(mappingUtility.mapToEntity(dto));
        } catch (Exception ex){
            log.error("Error saving New Consultant Data {} at {}",jsonUtility.writeToJson(dto, Constants.SAVE_CONSULTANT), LocalDateTime.now());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", ex);
        }
        return mappingUtility.mapToDto(saved);
    }

    @Cacheable
    private void validateEmailAndPhoneRecordsExistInDatabase(ConsultantDTO dto) {
        if (consultantRepository.existsByPhone(dto.getPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Record with email " + dto.getEmail() + " already exists");
        }
        if (consultantRepository.existsByEmail(dto.getPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Record with phone " + dto.getPhone() + " already exists");
        }
    }
}
