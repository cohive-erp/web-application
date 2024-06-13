package backend.cohive.Relatorio.Service;

import backend.cohive.Estoque.Dtos.ProdutoVendidoDto;
import backend.cohive.Estoque.Entidades.Produto;
import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Strategy.RelatorioTransacao;
import backend.cohive.Relatorio.Repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public ProdutoVendidoDto findMostSoldProduct() {
        Pageable topOne = PageRequest.of(0, 1);
        Page<Object[]> result = transacaoEstoqueRepository.findMostSoldProduct(topOne);
        if (!result.isEmpty()) {
            Object[] mostSoldProductData = result.getContent().get(0);
            Produto produto = (Produto) mostSoldProductData[0];
            Integer quantidadeVendida = ((Long) mostSoldProductData[1]).intValue();
            return new ProdutoVendidoDto(produto, quantidadeVendida);
        }
        return null;
    }

    public List<BigDecimal> generateMonthlyInvoicesForLastSixMonths() {
        List<BigDecimal> monthlyInvoices = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            LocalDateTime startDate = LocalDateTime.now().minusMonths(i).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
            List<TransacaoEstoque> transactions = transacaoEstoqueRepository.findAllByDateRange(startDate, endDate);
            System.out.println(transactions); // Imprime a lista de transações
            BigDecimal monthlyInvoice = transactions.stream()
                    .filter(t -> t.getTipoTransacao().equals("SAIDA"))
                    .map(t -> BigDecimal.valueOf(t.getEstoque().getProduto().getPrecoVenda()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            monthlyInvoices.add(monthlyInvoice);
        }
        return monthlyInvoices;
    }

    public BigDecimal generateDailyInvoice() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
        List<TransacaoEstoque> transactions = transacaoEstoqueRepository.findAllByDateRange(startOfDay, endOfDay);
        System.out.println(transactions); // Imprime a lista de transações
        BigDecimal dailyInvoice = transactions.stream()
                .filter(t -> t.getTipoTransacao().equals("SAIDA"))
                .map(t -> BigDecimal.valueOf(t.getEstoque().getProduto().getPrecoVenda()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return dailyInvoice;
    }

    public BigDecimal getSalesValueLastSevenDays() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime now = LocalDateTime.now();
        List<TransacaoEstoque> transactions = transacaoEstoqueRepository.findAllByDateRange(sevenDaysAgo, now);
        return transactions.stream()
                .filter(t -> t.getTipoTransacao().equals("SAIDA"))
                .map(t -> BigDecimal.valueOf(t.getEstoque().getProduto().getPrecoVenda()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
