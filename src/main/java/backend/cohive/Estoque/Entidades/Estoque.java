package backend.cohive.Estoque.Entidades;

import backend.cohive.Loja.Entidades.Loja;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Estoque {
    @Id
    private LocalDateTime dataEntradaInicial;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Loja loja;
    private Integer quantidade;

    public LocalDateTime getDataEntradaInicial() {
        return dataEntradaInicial;
    }

    public void setDataEntradaInicial(LocalDateTime dataEntradaInicial) {
        this.dataEntradaInicial = dataEntradaInicial;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}



