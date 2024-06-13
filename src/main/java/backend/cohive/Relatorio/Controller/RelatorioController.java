package backend.cohive.Relatorio.Controller;

import backend.cohive.Estoque.Dtos.ProdutoVendidoDto;
import backend.cohive.Estoque.Entidades.Produto;
import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.Relatorio.Dtos.RelatorioMapper;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/produto-mais-vendido")
    public ResponseEntity<ProdutoVendidoDto> getMostSoldProduct() {
        ProdutoVendidoDto produtoVendido = relatorioService.findMostSoldProduct();
        if (produtoVendido != null) {
            return ResponseEntity.ok(produtoVendido);
        }
        return ResponseEntity.notFound().build();
    }

}
