package com.consultancy.project.service.consultant;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;
import com.consultancy.project.util.ConsultantMapper;
import com.consultancy.project.util.PayloadValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;



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
    public ConsultantDTO findById(Long id) {
        ConsultantDTO dto;
        if (id > 0){
            dto = consultantDbService.findById(id).map(consultantMapper::toDto)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consultant not found by id " + id));
        } else {
            log.error("Id must be value greater than 0 {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be value greater than 0");
        }
        return dto;
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
