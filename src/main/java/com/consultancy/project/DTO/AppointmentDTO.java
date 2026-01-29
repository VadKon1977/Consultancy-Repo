package com.consultancy.project.DTO;

import com.consultancy.project.util.Constants;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id; // Required for response

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Consultant ID is required")
    private Long consultantId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Schedule time is required")
    @Future(message = "Appointment must be in the future")
    private LocalDateTime scheduledAt;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = Constants.STATUS_REGEX,
            message = "Invalid status. Use: PENDING, CONFIRMED, CANCELLED or COMPLETED")
    private String status;
}
