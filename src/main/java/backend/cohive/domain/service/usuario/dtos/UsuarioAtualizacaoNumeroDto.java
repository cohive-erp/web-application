package backend.cohive.domain.service.usuario.dtos;

import jakarta.validation.constraints.*;

public class UsuarioAtualizacaoNumeroDto {
    @Pattern(regexp="^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}-?[0-9]{4}$", message="Número de celular inválido")
    private String numeroCelular;


    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }
}
