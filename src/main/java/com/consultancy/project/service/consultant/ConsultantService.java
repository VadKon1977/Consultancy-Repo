package com.consultancy.project.service.consultant;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.util.ConsultantMapper;
import com.consultancy.project.util.MappingUtility;
import com.consultancy.project.util.PayloadValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;


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
              throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Consultant has not been saved " + LocalDateTime.now());
          }
       }
        return saved;
    }

    @Override
    public ConsultantDTO findById(Long id) {
        ConsultantDTO dto;
        if (id > 0){
            dto = consultantDbService.findById(id).map(consultantMapper::toDto)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consultant id {} not found in Database"));
        } else {
            log.error("Id must be value greater than 0 {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be value greater than 0");
        }
        return dto;
    }

    @Override
    public List<ConsultantDTO> findAll() {
        return null;
    }

    @Override
    public ConsultantDTO update(Long id, ConsultantDTO consultantDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public ConsultantDTO patch(Long id, ConsultantDTO patchDto) {
        return null;
    }
}
