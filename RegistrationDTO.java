package com.banketa.banketa.DTO;

import com.banketa.banketa.Custom.NotBlankAndNotSpaces;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    @NotBlank(message="Field must not be empty")
    @Size(min = 4, max = 12)
    private String username;
    @NotBlank(message="Field must not be empty")
    @Email(message="Invalid email format")
    private String email;
    @NotBlankAndNotSpaces(message="Field must not be empty and no spaces allowed")
    @Size(min = 8)
    private String password;
    @NotBlank(message="Field must not be empty")
    private String firstname;
    private String middlename;
    @NotBlank(message="Field must not be empty")
    private String lastname;
}
