package backend.cohive.Entidades;

import java.time.LocalDateTime;

public class Alerta {
    private String mensagem;
    private LocalDateTime data;
    private String tipo;
    private Integer idAlerta;

    public Alerta(String mensagem, LocalDateTime data, String tipo) {
        this.mensagem = mensagem;
        this.data = data;
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
