package backend.cohive.Relatorio.Strategy;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;

import java.util.List;

public interface Relatorio {
    public void gerarCsv(String nomeArq, List<TransacaoEstoque> transacaoEstoqueLista);
}
