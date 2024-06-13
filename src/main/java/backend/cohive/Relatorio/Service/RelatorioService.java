package backend.cohive.Relatorio.Service;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Strategy.RelatorioTransacao;
import backend.cohive.Relatorio.Repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

    private static final Logger logger = Logger.getLogger(RelatorioService.class.getName());

    public void gerarRelatorioMensalCSV(String nomeArquivo, Integer mes, Integer ano) {
        logger.info("Gerando relatório para o ano: " + ano + " e mês: " + mes);

        // Calculando datas de início e fim do mês
        LocalDateTime inicioMes = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fimMes = inicioMes.plusMonths(1).minusSeconds(1);

        // Obtendo as transações mensais do repositório
        List<TransacaoEstoque> transacoesSaida = transacaoEstoqueRepository.findAllByYearAndMonth(ano, mes);

        logger.info("Número de transações encontradas: " + transacoesSaida.size());

        // Verificando se alguma das listas está vazia
        if (transacoesSaida.isEmpty()) {
            throw new RuntimeException("Não há transações para gerar o relatório");
        }

        // Criando uma lista única contendo transações de saída e de entrada nova
        List<TransacaoEstoque> transacoesMensais = new ArrayList<>();
        transacoesMensais.addAll(transacoesSaida);

        // Gerando o relatório mensal
        RelatorioTransacao relatorioMensal = new RelatorioTransacao();
        relatorioMensal.gerarCsv(nomeArquivo, transacoesMensais);
    }

    public Resource getRelatorioAsResource(String nomeArquivo) throws MalformedURLException {
        Path path = Paths.get(nomeArquivo);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Não foi possível ler o arquivo " + nomeArquivo);
        }
    }
}
