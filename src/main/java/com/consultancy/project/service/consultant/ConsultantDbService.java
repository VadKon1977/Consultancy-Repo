package com.consultancy.project.service.consultant;

import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.repository.ConsultantRepository;
import com.consultancy.project.util.Constants;
import com.consultancy.project.util.ConsultantMapper;
import com.consultancy.project.util.JsonUtility;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ConsultantDbService implements IConsultantDbService {

    private final ConsultantRepository consultantRepository;
    private final JsonUtility jsonUtility;
    private final ConsultantMapper consultantMapper;

    @Override
    @Transactional
    public ConsultantDTO save(ConsultantDTO dto) {
        ConsultantEntity saved;
        log.info("Saving New Consultant Data {} at {}",jsonUtility.writeToJson(dto, Constants.SAVE_CONSULTANT) , LocalDateTime.now());
        validateEmailAndPhoneRecordsExistInDatabase(dto);
        try {
            saved = consultantRepository.save(consultantMapper.toEntity(dto));
        } catch (Exception ex){
            log.error("Error saving New Consultant Data {} at {}",jsonUtility.writeToJson(dto, Constants.SAVE_CONSULTANT), LocalDateTime.now());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", ex);
        }
        return consultantMapper.toDto(saved);
    }

    private void validateEmailAndPhoneRecordsExistInDatabase(ConsultantDTO dto) {
        if (consultantRepository.existsByPhone(dto.getPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Record with email " + dto.getEmail() + " already exists");
        }
        if (consultantRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Record with phone " + dto.getPhone() + " already exists");
        }
    }

    @Override
    @Transactional
    @Cacheable (value = "consultants", key ="#id")
    public Optional<ConsultantEntity>  findById(Long id) {
        Optional<ConsultantEntity> byId;
        try{
            byId = consultantRepository.findById(id);
        } catch (Exception ex) {
            log.error("Database error when retrieving consultant by Id {}", id);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", ex);
        }
        return byId;
    }

    @Override
    public List<ConsultantEntity> findAll() {
        log.info("[TracingId {}] Retrieving All Consultants at ", MDC.get(Constants.TRACE_ID_KEY));
        List<ConsultantEntity> all;
        try {
            all = consultantRepository.findAll();
        } catch (Exception ex) {
            log.error("Database error when retrieving consultant by Id");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", ex);
        }
        return all;
    }
}
