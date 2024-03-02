package backend.cohive;

public class Usuario {
    private String nome;
    private String sobrenome;
    private String numeroCelular;
    private String email;
    private String senha;
    private String nivelAcesso;

    public Usuario(){}

    public Usuario(String nome, String sobrenome, String numeroCelular, String email, String senha, String nivelAcesso) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.numeroCelular = numeroCelular;
        this.email = email;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", numeroCelular='" + numeroCelular + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", nivelAcesso='" + nivelAcesso + '\'' +
                '}';
    }
}
