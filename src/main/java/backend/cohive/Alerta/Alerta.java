package backend.cohive.Alerta;

import java.time.LocalDateTime;

public abstract class Alerta {
    private String tipo;
    private LocalDateTime data;
    private String mensagem;

    public Alerta(String tipo, LocalDateTime data, String mensagem) {
        this.tipo = tipo;
        this.data = data;
        this.mensagem = mensagem;
    }

    public abstract void gerarAlerta();

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
