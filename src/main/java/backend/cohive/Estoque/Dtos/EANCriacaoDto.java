package backend.cohive.Estoque.Dtos;

import backend.cohive.Loja.Entidades.Loja;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class EANCriacaoDto {
    private String ean;
    private String nome;
    @NotNull
    @Positive
    private double precoVenda;

    @NotNull
    @Positive
    private double precoCompra;

    @Positive
    @NotNull
    private int quantidade;
    private Loja loja;

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }
}
