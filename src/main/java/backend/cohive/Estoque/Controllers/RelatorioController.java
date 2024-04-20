//package backend.cohive.Estoque.Controllers;
//
//import backend.cohive.Observer.RelatorioMensal;
//import backend.cohive.Observer.RelatorioMensalCategoria;
//import backend.cohive.ControleEstoque;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import java.util.Date;
//
//@RestController
//@RequestMapping("/relatorios")
//public class RelatorioController {
//
//
//    @GetMapping("/relatorio-mensal")
//    public ResponseEntity<String> gerarRelatorioMensal() {
//        Date data = new Date(); // Obtém a data atual
//        String descricao = "Relatório Mensal de Produtos por Preço";
//
//        RelatorioMensal relatorioMensal = new RelatorioMensal(data, descricao, controleEstoque);
//        relatorioMensal.gerarRelatorio();
//
//        return ResponseEntity.status(200).body("Relatório mensal gerado com sucesso.");
//    }
//
//    @GetMapping("/relatorio-mensal-por-categoria")
//    public ResponseEntity<String> gerarRelatorioMensalPorCategoria() {
//        Date data = new Date(); // Obtém a data atual
//        String descricao = "Relatório Mensal de Produtos por Categoria";
//
//        RelatorioMensalCategoria relatorioMensalCategoria = new RelatorioMensalCategoria(data, descricao, controleEstoque);
//        relatorioMensalCategoria.gerarRelatorio();
//
//        return ResponseEntity.status(200).body("Relatório mensal por categoria gerado com sucesso.");
//    }
//}
