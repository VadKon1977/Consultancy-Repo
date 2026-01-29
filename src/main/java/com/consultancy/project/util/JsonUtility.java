package com.consultancy.project.util;

import com.consultancy.project.DTO.ConsultantDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j //Lombok generates: private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JsonUtility.class);
public class JsonUtility {

    private final ObjectMapper objectMapper;

    public JsonUtility(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String writeToJson(ConsultantDTO dto, String flow) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(dto);
        } catch(JsonProcessingException ex) {
            log.error("Consultant dto json conversion error in {} error{} at {}", flow, ex.toString(), LocalDateTime.now());
            throw new RuntimeException("Failed to convert ConsultantDTO to JSON", ex);
        }
        return json != null ? json : "json value is null occured during consultant conversion";
    }
}
