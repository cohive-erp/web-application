package backend.cohive.domain.service.usuario.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

public class UsuarioCriacaoDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;
    @Pattern(regexp="^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}-?[0-9]{4}$", message="Número de celular inválido")
    private String numeroCelular;
    @NotBlank
    @Email
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
