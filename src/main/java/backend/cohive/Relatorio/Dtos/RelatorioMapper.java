package backend.cohive.Relatorio.Dtos;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;

import java.time.LocalDate;
import java.util.List;

public class RelatorioMapper {
    public static RelatorioEntidade fromTransacaotoRelatorioEntidade(){

        RelatorioEntidade relatorioEntidade = new RelatorioEntidade();

        relatorioEntidade.setDataCriacao(LocalDate.now());
        relatorioEntidade.setDescricao("Relatorio gerado com os dados com todas as transações");

        return relatorioEntidade;
    }
}
