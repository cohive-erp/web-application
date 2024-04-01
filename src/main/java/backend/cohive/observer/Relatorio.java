package backend.cohive.observer;

import java.util.Date;

public abstract class Relatorio {
    private Date data;
    private String descricao;

    public Relatorio(Date data, String descricao) {
        this.data = data;
        this.descricao = descricao;
    }

    public abstract void gerarRelatorio();

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
