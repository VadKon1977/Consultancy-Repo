package com.consultancy.project.util;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsultantMapper {
    ConsultantDTO toDto(ConsultantEntity consultantEntity);
    ConsultantEntity toEntity(ConsultantDTO consultantDTO);
}
