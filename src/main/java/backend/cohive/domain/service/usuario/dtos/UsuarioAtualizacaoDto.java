package backend.cohive.domain.service.usuario.dtos;

import jakarta.validation.constraints.*;

public class UsuarioAtualizacaoDto {
    @NotBlank(message = "O nome não pode estar vazio")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @Pattern(regexp="^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}-?[0-9]{4}$", message="Número de celular inválido")
    private String numeroCelular;

    @NotBlank(message = "O e-mail não pode estar vazio")
    @Email(message = "E-mail inválido")
    @Size(min = 7, max = 100, message = "O e-mail deve ter entre 7 e 100 caracteres")
    private String email;

    @Size(min = 8, max = 50, message = "A senha deve ter entre 8 e 50 caracteres")
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
