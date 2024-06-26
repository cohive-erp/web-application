package backend.cohive.domain.service.usuario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class UsuarioCriacaoDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;
    @Pattern(regexp="^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}-?[0-9]{4}$", message="Número de celular inválido")
    private String numeroCelular;
    @NotBlank
    @Email
    @Schema(description = "Email do usuário", example = "rafael.reis@sptech.school")
    @Size(min = 7, max = 100)
    private String email;
    @Size(min = 8, max = 50)
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
