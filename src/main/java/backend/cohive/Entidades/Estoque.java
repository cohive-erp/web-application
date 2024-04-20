package backend.cohive.Entidades;

import backend.cohive.Loja.Loja;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

@Entity
public class Estoque {

    @Id
    private LocalDate dataEntrada;

    @ManyToOne
    @JoinColumn(name = "id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "loja")
    private Loja loja;

    private int quantidade;

    public Estoque(){}

    public Estoque(LocalDate dataEntrada, Produto produto, Loja loja, int quantidade) {
        this.dataEntrada = dataEntrada;
        this.produto = produto;
        this.loja = loja;
        this.quantidade = quantidade;
    }

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



