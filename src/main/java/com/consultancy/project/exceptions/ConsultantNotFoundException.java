package com.consultancy.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultantNotFoundException extends RuntimeException{

    private String error;
    private String exception;
    private int status;
}
