package com.consultancy.project.service.consultant;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;

import java.util.List;

public interface IConsultantService {
    ConsultantDTO save(ConsultantDTO dto);
    ConsultantEntity findById(Long id);
    List<ConsultantDTO> findAll();
    ConsultantDTO update(Long id, ConsultantDTO consultantDTO);
    void delete(Long id);
    ConsultantDTO patch(Long id, ConsultantDTO patchDto);
}
