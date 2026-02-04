package com.consultancy.project.util;

import com.consultancy.project.DAO.ConsultantEntity;
import com.consultancy.project.DTO.ConsultantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsultantMapper {
    ConsultantDTO toDto(ConsultantEntity consultantEntity);
    ConsultantEntity toEntity(ConsultantDTO consultantDTO);

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "secondName", source = "secondName")
    @Mapping(target = "specialization", source = "specialization")
    @Mapping(target = "experienceYears", source = "experienceYears")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "id", ignore = true)
    ConsultantEntity updateEntityFromDto(ConsultantDTO dto, @MappingTarget ConsultantEntity consultantEntity);
}
