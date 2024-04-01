package backend.cohive.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;
    @NotBlank
    @Size(min = 2, max = 50)
    private String sobrenome;

    private String numeroCelular;
    @NotBlank
    @Email
    @Size(min = 7, max = 100)
    private String email;
    @Size(min = 8, max = 50)
    private String senha;
    @NotNull
    private String nivelAcesso;
    @CreationTimestamp
    private LocalDate dataCadastro;

    public Usuario(){}

    public Usuario(String nome, String sobrenome, String numeroCelular, String email, String senha, String nivelAcesso, LocalDate dataCadastro) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.numeroCelular = numeroCelular;
        this.email = email;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
        this.dataCadastro = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
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

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
