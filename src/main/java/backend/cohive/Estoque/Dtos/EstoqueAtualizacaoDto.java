package backend.cohive.Estoque.Dtos;

import backend.cohive.Estoque.Entidades.Produto;
import backend.cohive.Loja.Entidades.Loja;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class EstoqueAtualizacaoDto {
    @NotNull
    @PastOrPresent
    private LocalDateTime dataEntradaInicial;
    @NotNull
    private Produto produto;
    @NotNull
    private Loja loja;
    @NotNull
    @Positive
    @Min(1)
    @Max(1)
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
