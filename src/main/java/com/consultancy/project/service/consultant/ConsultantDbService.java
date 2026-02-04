package com.consultancy.project.service.consultant;

import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.exceptions.ConsultantNotFoundException;
import com.consultancy.project.exceptions.DatabaseErrorException;
import com.consultancy.project.exceptions.RecordExistsException;
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

import java.util.List;
import java.util.Objects;
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
        log.info("[TracingId {}] Saving New Consultant Data {} at {}",MDC.get(Constants.TRACE_ID_KEY), jsonUtility.writeToJson(dto, Constants.SAVE_CONSULTANT) , MDC.get(Constants.REQUEST_TIME));
        validateEmailAndPhoneRecordsExistInDatabase(dto);
        try {
            saved = consultantRepository.save(consultantMapper.toEntity(dto));
        } catch (Exception ex){
            log.error("[TracingId {}] Error saving New Consultant Data {} at {}", MDC.get(Constants.TRACE_ID_KEY),jsonUtility.writeToJson(dto, Constants.SAVE_CONSULTANT), MDC.get(Constants.REQUEST_TIME));
            throw new DatabaseErrorException(ex.getMessage(), "Database error when saving consultant", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return consultantMapper.toDto(saved);
    }

    @Override
    @Transactional
    @Cacheable (value = "consultants", key ="#id")
    public Optional<ConsultantEntity>  findById(Long id) {
        log.info("[TracingId {}] Retrieving Consultants by id {} at {} ", MDC.get(Constants.TRACE_ID_KEY), id, MDC.get(Constants.REQUEST_TIME));
        Optional<ConsultantEntity> byId;
        try{
            byId = consultantRepository.findById(id);
        } catch (Exception ex) {
            log.error("[TracingId {}] Database error when retrieving consultant by Id {} at {}", id, MDC.get(Constants.TRACE_ID_KEY), MDC.get(Constants.REQUEST_TIME));
            throw new DatabaseErrorException(ex.getMessage(), "Database error when getting consultant by id", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return byId;
    }

    @Override
    @Transactional
    public List<ConsultantEntity> findAll() {
        log.info("[TracingId {}] Retrieving All Consultants at {} ", MDC.get(Constants.TRACE_ID_KEY), MDC.get(Constants.REQUEST_TIME));
        List<ConsultantEntity> all;
        try {
            all = consultantRepository.findAll();
        } catch (Exception ex) {
            log.error("[TracingId {}] Database error when retrieving consultant by Id at {}", MDC.get(Constants.TRACE_ID_KEY), MDC.get(Constants.REQUEST_TIME));
            throw new DatabaseErrorException(ex.getMessage(), "Database error when getting a list of all consultants", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return all;
    }

    @Override
    @Transactional
    public  ConsultantDTO update(ConsultantDTO dto, ConsultantEntity consultantEntity) {
        log.info("[TracingId {}] Updating Consultant Data {} at {}",MDC.get(Constants.TRACE_ID_KEY),
                jsonUtility.writeToJson(dto, Constants.UPDATE), MDC.get(Constants.REQUEST_TIME));
        if (Objects.isNull(consultantEntity)) {
            log.error("[TracingId {}] Consultant data is null for request id {} at {}",MDC.get(Constants.TRACE_ID_KEY),
                    dto.getId(), MDC.get(Constants.REQUEST_TIME));
            throw new ConsultantNotFoundException("Consultant data is null in db for id " + dto.getId(), "Database Error", HttpStatus.NOT_FOUND.value());
        }
        try {
            ConsultantEntity updatedConsultant = consultantMapper.updateEntityFromDto(dto, consultantEntity);
            return  consultantMapper.toDto(updatedConsultant);
        } catch (Exception ex) {
            log.error("[TracingId:{}] Data Transformation Error for Id {} at {}", MDC.get(Constants.TRACE_ID_KEY), dto.getId(), MDC.get(Constants.REQUEST_TIME));
            throw new DatabaseErrorException("Data Transformation Error", "MAPPER ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void validateEmailAndPhoneRecordsExistInDatabase(ConsultantDTO dto) {
        if (consultantRepository.existsByPhone(dto.getPhone())) {
            throw new RecordExistsException("Record with email " + dto.getPhone() + " already exists", HttpStatus.CONFLICT.value(), Constants.RECORD_EXISTS);
        }
        if (consultantRepository.existsByEmail(dto.getEmail())) {
            throw new RecordExistsException("Record with phone " + dto.getEmail() + " already exists", HttpStatus.CONFLICT.value(), Constants.RECORD_EXISTS);
        }
    }
}
