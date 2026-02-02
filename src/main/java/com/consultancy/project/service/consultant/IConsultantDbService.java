package com.consultancy.project.service.consultant;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;

import java.util.Optional;

public interface IConsultantDbService {

    ConsultantDTO save(ConsultantDTO dto);
    Optional<ConsultantEntity> findById(Long id);
}
