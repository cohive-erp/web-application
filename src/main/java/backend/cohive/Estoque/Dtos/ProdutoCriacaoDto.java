package backend.cohive.Estoque.Dtos;

import backend.cohive.Loja.Entidades.Loja;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

public class ProdutoCriacaoDto {
    @NotBlank
    @Size(min = 3, max = 100)
    @NotNull
    private String nome;

    @NotBlank
    @Size(min = 2, max = 50)
    private String fabricante;

    @NotBlank
    @Size(min = 2, max = 50)
    private String categoria;

    @NotNull
    @Positive
    private double precoVenda;

    @NotNull
    @Positive
    private double precoCompra;
    @NotBlank
    @Size(min = 3, max = 200)
    private String descricao;
    @Positive
    @NotNull
    private int quantidade;
    @NotNull
    private Loja loja;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
