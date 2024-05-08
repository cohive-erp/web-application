package backend.cohive.Relatorio.Service;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Observer.RelatorioTransacao;
import backend.cohive.Relatorio.Repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {
    @Autowired
    private TransacaoEstoqueRepository transacaoEstoqueRepository;
    @Autowired
    private RelatorioRepository relatorioRepository;

    public RelatorioEntidade criar(RelatorioEntidade relatorioEntidade){
        return relatorioRepository.save(relatorioEntidade);
    }

    public void gerarRelatorioTransacoesCSV(String nomeArquivo) {
        // Obtendo as transações mensais do repositório
        List<TransacaoEstoque> transacaoEstoqueLista = transacaoEstoqueRepository.findAll();

        if (transacaoEstoqueLista.isEmpty()) {
            throw new RuntimeException("Não há transações para gerar o relatório");
        }

        // Gerando o relatório mensal
        RelatorioTransacao relatorioTransacao = new RelatorioTransacao();
        relatorioTransacao.gerarCsv(nomeArquivo, transacaoEstoqueLista);
    }

    public void gerarRelatorioMensalCSV(String nomeArquivo, Integer mes, Integer ano) {
        // Calculando datas de início e fim do mês
        LocalDateTime inicioMes = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fimMes = inicioMes.plusMonths(1).minusSeconds(1);

        // Obtendo as transações mensais do repositório
        List<TransacaoEstoque> transacoesSaida = transacaoEstoqueRepository.findByDataSaidaBetween(inicioMes, fimMes);
        List<TransacaoEstoque> transacoesEntradaNova = transacaoEstoqueRepository.findByDataEntradaNovaBetween(inicioMes, fimMes);

        // Verificando se alguma das listas está vazia
        if (transacoesSaida.isEmpty() && transacoesEntradaNova.isEmpty()) {
            throw new RuntimeException("Não há transações para gerar o relatório");
        }

        // Criando uma lista única contendo transações de saída e de entrada nova
        List<TransacaoEstoque> transacoesMensais = new ArrayList<>();
        transacoesMensais.addAll(transacoesSaida);
        transacoesMensais.addAll(transacoesEntradaNova);

        // Gerando o relatório mensal
        RelatorioTransacao relatorioMensal = new RelatorioTransacao();
        relatorioMensal.gerarCsv(nomeArquivo, transacoesMensais);
    }
}
