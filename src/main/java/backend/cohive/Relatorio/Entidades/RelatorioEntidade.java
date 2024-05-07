package backend.cohive.Relatorio.Entidades;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RelatorioEntidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRelatorio;
    private LocalDate dataCriacao;
    private String descricao;

    public Integer getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(Integer idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
