package backend.cohive.Estoque.Dtos;

import backend.cohive.Entidades.Produto;
import backend.cohive.Loja.Loja;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class EstoqueAtualizacaoDto {
    @NotNull
    @PastOrPresent
    private LocalDate dataEntrada;
    @NotNull
    private Produto produto;
    @NotNull
    private Loja loja;
    @NotNull
    @Positive
    private int quantidade;

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
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
