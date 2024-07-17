package gr.unipi.javaspot.dtos;

import gr.unipi.javaspot.validators.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 8, max = 30, message = "Username must be between 8 and 30 characters.")
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9_]{7,29}$",
            message = "Username must start with a letter and contain only letters, digits, and underscores."
    )
    @UniqueUsername
    private String username;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!.<>])(?!.* ).{8,64}$",
            message = "Password must contain at least one letter, one digit, one special character (@#$%^&+=!.<>), and no spaces."
    )
    private String password;

}
