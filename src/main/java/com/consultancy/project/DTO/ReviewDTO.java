package com.consultancy.project.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id; // required for response

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    @NotNull(message = "Rating is required")
    private Integer rating;

    @Size(max = 2000, message = "Comment is too long (max 2000 characters)")
    private String comment;
}
