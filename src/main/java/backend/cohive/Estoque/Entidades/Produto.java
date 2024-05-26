package backend.cohive.Estoque.Entidades;

import backend.cohive.Loja.Entidades.Loja;
import jakarta.persistence.*;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduto;
    private String nome;
    private String fabricante;
    private String categoria;
    private Double precoVenda;
    private Double precoCompra;
    @ManyToOne
    @JoinColumn(name = "loja")
    private Loja loja;

    public Produto() {}

    public Produto(Integer idProduto, String nome, String fabricante, String categoria, Double precoVenda, Double precoCompra) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.precoVenda = precoVenda;
        this.precoCompra = precoCompra;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

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

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(Double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }
}


