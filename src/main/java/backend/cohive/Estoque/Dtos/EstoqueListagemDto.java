package backend.cohive.Estoque.Dtos;

import backend.cohive.Estoque.Entidades.Produto;
import backend.cohive.Loja.Entidades.Loja;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class EstoqueListagemDto {
    private LocalDateTime dataEntradaInicial;
    private Produto produto;
    private Loja loja;
    private int quantidade;

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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
