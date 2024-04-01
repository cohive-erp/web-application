package backend.cohive.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    @NotBlank
    @Email
    @Size(min = 7, max = 100)
    private String email;
    @Size(min = 8, max = 50)
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return senha;
    }
}
