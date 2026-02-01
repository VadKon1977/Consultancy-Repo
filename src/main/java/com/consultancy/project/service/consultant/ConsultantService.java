package com.consultancy.project.service.consultant;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.util.PayloadValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
        ConsultantDTO dto;
        if (id > 0){
            dto = consultantDbService.findById(id).map(this::convertToConsultantDTO)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consultant id {} not found in Database"));
        } else {
            log.error("Id must be value greater than 0 {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be value greater than 0");
        }
        return dto;
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

    private ConsultantDTO convertToConsultantDTO(ConsultantEntity entity) {
        ConsultantDTO consultantDTO = null;
        consultantDTO = new ConsultantDTO();
        consultantDTO.setId(entity.getId());
        consultantDTO.setEmail(entity.getEmail());
        consultantDTO.setPhone(entity.getPhone());
        consultantDTO.setSpecialization(entity.getSpecialization());
        consultantDTO.setFirstName(entity.getFirstName());
        consultantDTO.setSecondName(entity.getSecondName());
        consultantDTO.setExperienceYears(entity.getExperienceYears());
        return consultantDTO;
    }
}
