package backend.cohive.Loja.Dtos;

import backend.cohive.domain.service.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class LojaCriacaoDto {
    @Positive
    @NotNull
    private Integer numero;
    @NotNull
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String CEP;
    @NotNull
    @org.hibernate.validator.constraints.br.CNPJ
    private String CNPJ;
    @NotNull
    private Usuario usuario;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
