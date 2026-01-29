package com.consultancy.project.util;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;
import org.springframework.stereotype.Component;

@Component
public class MappingUtility {

    public ConsultantEntity mapToEntity(ConsultantDTO dto) {
        ConsultantEntity consultantEntity = new ConsultantEntity();
        consultantEntity.setFirstName(dto.getFirstName());
        consultantEntity.setSecondName(dto.getSecondName());
        consultantEntity.setSpecialization(dto.getSpecialization());
        consultantEntity.setExperienceYears(dto.getExperienceYears());
        consultantEntity.setEmail(dto.getEmail());
        consultantEntity.setPhone(dto.getPhone());
        return consultantEntity;
    }

    public ConsultantDTO mapToDto(ConsultantEntity entity) {
        ConsultantDTO dto = new ConsultantDTO();
        dto.setId(entity.getId()); // Важно вернуть ID, созданный базой
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setSpecialization(entity.getSpecialization());
        dto.setExperienceYears(entity.getExperienceYears());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        return dto;
    }
}
