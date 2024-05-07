package backend.cohive.Relatorio.Service;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Observer.RelatorioTransacao;
import backend.cohive.Relatorio.Repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

    public void gerarRelatoriotransacoesCSV(String nomeArquivo) {
        // Obtendo as transações mensais do repositório
        List<TransacaoEstoque> transacaoEstoqueLista = transacaoEstoqueRepository.findAll();

        if (transacaoEstoqueLista.isEmpty()) {
            throw new RuntimeException("Não há transações para gerar o relatório");
        }

        // Gerando o relatório mensal
        RelatorioTransacao relatorioMensal = new RelatorioTransacao();
        relatorioMensal.gerarCsv(nomeArquivo, transacaoEstoqueLista);
    }
}
