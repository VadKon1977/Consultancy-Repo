package com.consultancy.project.service.consultant;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.exceptions.ConsultantNotFoundException;
import com.consultancy.project.util.Constants;
import com.consultancy.project.util.ConsultantMapper;
import com.consultancy.project.util.PayloadValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
public class ConsultantService implements IConsultantService{

    private final PayloadValidator payloadValidator;
    private final ConsultantDbService consultantDbService;
    private final ConsultantMapper consultantMapper;

    @Override
    public ConsultantDTO save(ConsultantDTO dto) {
        ConsultantDTO saved = null;
       if (payloadValidator.consultantValidator(dto)) {
          saved  = consultantDbService.save(dto);
          if (null == saved) {
              throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Consultant has not been saved ");
          }
       }
        return saved;
    }

    @Override
    public ConsultantEntity findById(Long id) {
        if (id <= 0){
            log.error("[TracingId:{}] Id must be value greater than 0 {} at {}", MDC.get(Constants.TRACE_ID_KEY), id, MDC.get(Constants.REQUEST_TIME));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be value greater than 0");
        }
        return consultantDbService.findById(id).orElseThrow(() -> new ConsultantNotFoundException("Consultant not found for id " + id, "Database Error", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public List<ConsultantDTO> findAll() {
        log.info("Fetching all consultants");
        List<ConsultantEntity> allConsultants = consultantDbService.findAll();
        if (allConsultants.isEmpty()) {
            log.error("Consultants are not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consultants not found");
        }

        return allConsultants.stream().filter(Objects::nonNull).map(a -> {
            ConsultantDTO dto;
            try {
                dto = consultantMapper.toDto(a);
            } catch (Exception ex) {
                log.error("Mapping all consultants error ", ex);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Mapping all consultants error ", ex);
            }
            return dto;
        }).toList();
    }

    @Override
    public ConsultantDTO update(Long id, ConsultantDTO dto) {
        log.info("[TracingId {}] Update Consultant by id {} at {}", MDC.get(Constants.TRACE_ID_KEY), id, MDC.get(Constants.REQUEST_TIME));
        ConsultantDTO updatedConsultant = null;
        if (payloadValidator.consultantValidator(dto)) {
            updatedConsultant = consultantDbService.update(dto, findById(id));
            if (Objects.isNull(updatedConsultant)) {
                log.error("[TracingId {}] Error Updating Consultant with id {} at {}", MDC.get(Constants.TRACE_ID_KEY), id, MDC.get(Constants.REQUEST_TIME));
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Consultant has not been updated ");
            }
        }
        return updatedConsultant;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public ConsultantDTO patch(Long id, ConsultantDTO patchDto) {
        return null;
    }
}
