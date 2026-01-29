package com.consultancy.project.service.consultant;

import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.util.PayloadValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class ConsultantService {

    private final PayloadValidator payloadValidator;
    private final ConsultantDbService consultantDbService;
    public ConsultantService(PayloadValidator payloadValidator,  ConsultantDbService consultantDbService) {
        this.payloadValidator = payloadValidator;
        this.consultantDbService = consultantDbService;
    }

    public ConsultantDTO save(ConsultantDTO dto) {
        ConsultantDTO saved = null;
       if (payloadValidator.consultantValidator(dto)) {
          saved  = consultantDbService.save(dto);
          if (null == saved) {
              throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Consultant has not been saved " + LocalDateTime.now());
          }
       }
        return saved;
    }

    public ConsultantDTO findById(Long id) {
        return null;
    }

    public List<ConsultantDTO> findAll() {
        return null;
    }

    public ConsultantDTO update(Long id, ConsultantDTO consultantDTO) {
        return null;
    }

    public void delete(Long id) {
    }

    public ConsultantDTO patch(Long id, ConsultantDTO patchDto) {
        return null;
    }
}
