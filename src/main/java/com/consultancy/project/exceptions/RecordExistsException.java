package com.consultancy.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecordExistsException extends RuntimeException {
    private String error;
    private int status;
    private String message;
}
