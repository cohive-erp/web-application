package backend.cohive.Relatorio.Controller;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.Relatorio.Dtos.RelatorioMapper;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/relatorio-transacoes")
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

    @GetMapping("/relatorio-mensal")
    public ResponseEntity<String> gerarRelatorioMensalCSV(@RequestParam String nomeArquivo, @RequestParam Integer mes, @RequestParam Integer ano) {
        try {
            relatorioService.gerarRelatorioMensalCSV(nomeArquivo, mes, ano);

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
}
