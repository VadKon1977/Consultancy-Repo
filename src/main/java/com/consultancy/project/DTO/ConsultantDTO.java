package com.consultancy.project.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultantDTO {

    private Long id; // used in responses

    @NotBlank(message = "first name is mandatory field")
    @Size(min = 2, max = 100)
    private String firstName;

    @NotBlank(message = "second name is mandatory field")
    @Size(min = 2, max = 100)
    private String secondName;

    @NotBlank(message = "specialization is a mandatory field")
    private String specialization;

    @Min(value = 0, message = "experience duration can not be negation")
    @Max(value = 50, message = "experience duration can not be more than 50 years")
    private Integer experienceYears;

    @NotBlank(message = "Email is a mandatory field in a request payload")
    @Email(message = "Email format is not correct")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email format is not correct")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "phone is a mandatory field in a request payload")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Phone format is not correct")
    @Size(max = 20)
    private String phone;
}
