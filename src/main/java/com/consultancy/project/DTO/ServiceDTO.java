package com.consultancy.project.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {

    private Long id;

    @NotBlank(message = "Service title is required")
    @Size(max = 255, message = "Title is too long")
    private String title;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 8, fraction = 2, message = "Price format is invalid (max 8 digits before and 2 after dot)")
    private BigDecimal pricePerHour;
}
