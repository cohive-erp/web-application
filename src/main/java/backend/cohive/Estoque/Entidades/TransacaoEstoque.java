package backend.cohive.Estoque.Entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TransacaoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransacaoEstoque;
    @ManyToOne
    @JoinColumn(name = "estoque")
    private Estoque estoque;
    private LocalDateTime dataSaida;
    private LocalDateTime dataEntradaNova;
    private Integer quantidadeAntesTransacao;
    private String tipoTransacao; // Adicionado para representar o tipo de transação

    public Integer getIdTransacaoEstoque() {
        return idTransacaoEstoque;
    }

    public void setIdTransacaoEstoque(Integer idTransacaoEstoque) {
        this.idTransacaoEstoque = idTransacaoEstoque;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public LocalDateTime getDataEntradaNova() {
        return dataEntradaNova;
    }

    public void setDataEntradaNova(LocalDateTime dataEntradaNova) {
        this.dataEntradaNova = dataEntradaNova;
    }

    public Integer getQuantidadeAntesTransacao() {
        return quantidadeAntesTransacao;
    }

    public void setQuantidadeAntesTransacao(Integer quantidadeAntesTransacao) {
        this.quantidadeAntesTransacao = quantidadeAntesTransacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
}
