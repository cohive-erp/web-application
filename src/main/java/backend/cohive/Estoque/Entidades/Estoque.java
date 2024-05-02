package backend.cohive.Estoque.Entidades;

import backend.cohive.Loja.Loja;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Estoque {
    @Id
    private LocalDateTime dataEntrada;

    @ManyToOne
    @JoinColumn(name = "id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "loja")
    private Loja loja;

    private int quantidade;

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
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



