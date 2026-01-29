package com.consultancy.project.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long id; // Used in responses

    @NotBlank(message = "first name is a mandatory field in a request payload")
    @Size(max = 255)
    private String firstName;

    @NotBlank(message = "second name is a mandatory field in a request payload")
    @Size(max = 255)
    private String secondName;

    @NotBlank(message = "Email is a mandatory field in a request payload")
    @Email(message = "Email format is not correct")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Email format is not correct")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "phone is a mandatory field in a request payload")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Phone format is not correct")
    private String phone;
}