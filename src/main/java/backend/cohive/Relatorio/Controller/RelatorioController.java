package backend.cohive.Relatorio.Controller;

import backend.cohive.Estoque.Dtos.ProdutoVendidoDto;
import backend.cohive.Relatorio.Dtos.RelatorioMapper;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping("/relatorio-transacoes")
    public ResponseEntity<String> gerarRelatorioTransacoesCSV(@RequestParam String nomeArquivo) {
        try {
            relatorioService.gerarRelatorioTransacoesCSV(nomeArquivo);

            RelatorioEntidade relatorioEntidade = RelatorioMapper.fromTransacaotoRelatorioEntidade();
            RelatorioEntidade relatorioEntidadeNovo = relatorioService.criar(relatorioEntidade);

            return ResponseEntity.ok(String.format(
                    "Relatório gerado com sucesso\nRelatório:\nIdRelatorio: %s\nDescrição: %s\nData Criação: %s",
                    relatorioEntidadeNovo.getIdRelatorio(),
                    relatorioEntidadeNovo.getDescricao(),
                    relatorioEntidadeNovo.getDataCriacao()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @GetMapping("/get-relatorio-csv")
    public ResponseEntity<Resource> getRelatorioCSV(@RequestParam String nomeArquivo) {
        try {
            Resource resource = relatorioService.getRelatorioAsResource(nomeArquivo);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/relatorio-mensal/{lojaId}")
    public ResponseEntity<String> gerarRelatorioMensalCSV(
            @RequestParam String nomeArquivo,
            @RequestParam Integer mes,
            @RequestParam Integer ano,
            @PathVariable Integer lojaId) {
        try {
            relatorioService.gerarRelatorioMensalCSV(nomeArquivo, mes, ano, lojaId);

            RelatorioEntidade relatorioEntidade = RelatorioMapper.fromTransacaotoRelatorioEntidade();
            RelatorioEntidade relatorioEntidadeNovo = relatorioService.criar(relatorioEntidade);

            return ResponseEntity.ok(String.format(
                    "Relatório gerado com sucesso\nRelatório:\nIdRelatorio: %s\nDescrição: %s\nData Criação: %s",
                    relatorioEntidadeNovo.getIdRelatorio(),
                    relatorioEntidadeNovo.getDescricao(),
                    relatorioEntidadeNovo.getDataCriacao()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar relatório mensal: " + e.getMessage());
        }
    }

    @GetMapping("/produto-mais-vendido/{lojaId}")
    public ResponseEntity<ProdutoVendidoDto> getMostSoldProductByLoja(@PathVariable Integer lojaId) {
        ProdutoVendidoDto produtoVendido = relatorioService.findMostSoldProductByLoja(lojaId);
        if (produtoVendido != null) {
            return ResponseEntity.ok(produtoVendido);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/faturas-mensais/{lojaId}")
    public ResponseEntity<List<BigDecimal>> getMonthlyInvoicesForLastSixMonthsByLoja(@PathVariable Integer lojaId) {
        List<BigDecimal> monthlyInvoices = relatorioService.generateMonthlyInvoicesForLastSixMonthsByLoja(lojaId);
        return ResponseEntity.ok(monthlyInvoices);
    }

    @GetMapping("/produtos-vendidos-mensal/{lojaId}")
    public ResponseEntity<List<Object[]>> getMonthlySoldProductsByLoja(@PathVariable Integer lojaId) {
        List<Object[]> monthlySoldProducts = relatorioService.getMonthlySoldProductsByLoja(lojaId);
        return ResponseEntity.ok(monthlySoldProducts);
    }

    @GetMapping("/fatura-diaria/{lojaId}")
    public ResponseEntity<BigDecimal> getDailyInvoiceByLoja(@PathVariable Integer lojaId) {
        BigDecimal dailyInvoice = relatorioService.generateDailyInvoiceByLoja(lojaId);
        return ResponseEntity.ok(dailyInvoice);
    }

    @GetMapping("/valor-vendas-ultimos-sete-dias/{lojaId}")
    public ResponseEntity<List<Object[]>> getSalesForLastSevenDaysByLoja(@PathVariable Integer lojaId) {
        List<Object[]> weeklySales = relatorioService.getSalesForLastSevenDaysByLoja(lojaId);
        return ResponseEntity.ok(weeklySales);
    }
}
