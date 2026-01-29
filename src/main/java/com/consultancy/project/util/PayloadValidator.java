package com.consultancy.project.util;

import com.consultancy.project.DTO.ConsultantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
@Slf4j
public class PayloadValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(Constants.PHONE_REGEX);
    public boolean consultantValidator(ConsultantDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DTO is missing");
        }

        if (dto.getExperienceYears() == null || dto.getExperienceYears() < 0 || dto.getExperienceYears() > 50) {
            log.error("Experience years validation failed: {}", dto.getExperienceYears());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Experience must be between 0 and 50 years");
        }

        boolean hasEmptyFields = Stream.of(
                dto.getFirstName(), dto.getSecondName(),
                dto.getSpecialization(), dto.getEmail(), dto.getPhone()
        ).anyMatch(field -> field == null || field.isBlank());

        if (hasEmptyFields) {
            log.error("Mandatory fields are missing in DTO: {}", dto);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All fields (Name, Specialization, Email, Phone) are mandatory");
        }

        if (!EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            log.error("Email regex failed: {}", dto.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }

        if (!PHONE_PATTERN.matcher(dto.getPhone()).matches()) {
            log.error("Phone regex failed: {}", dto.getPhone());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone format. Expected something like +1 (123) 456-7890");
        }
        return true;
    }
}
