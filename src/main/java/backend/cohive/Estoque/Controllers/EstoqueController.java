package backend.cohive.Estoque.Controllers;

import backend.cohive.Estoque.Dtos.*;
import backend.cohive.Estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping("/preencher-produto")
    public ResponseEntity<ProdutoListagemDto> preencherECadastrarProduto(@RequestBody EANCriacaoDto eanCriacaoDto) {
        return estoqueService.preencherECadastrarProduto(eanCriacaoDto);
    }

//    @GetMapping("/preencher-dados")
//    public ResponseEntity<ProdutoCriacaoDto> preencherDadosProduto(@RequestParam String ean) {
//        return estoqueService.preencherDadosProduto(ean);
//    }

    @PostMapping
    public ResponseEntity<ProdutoListagemDto> cadastrarProdutoNovo(@RequestBody ProdutoCriacaoDto produtoCriacaoDto) {
        return estoqueService.cadastrarProdutoNovo(produtoCriacaoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoListagemDto> procurarProdutoPorId(@PathVariable Integer id) {
        return estoqueService.procurarProdutoPorId(id);
    }

    @PutMapping("/atualizar-produto/{id}")
    public ResponseEntity<ProdutoListagemDto> atualizarProduto(@PathVariable Integer id, @RequestBody ProdutoAtualizacaoDto produtoAtualizacaoDto) {
        return estoqueService.atualizarProduto(id, produtoAtualizacaoDto);
    }

    @PutMapping("/baixa-estoque")
    public ResponseEntity<EstoqueListagemDto> darBaixaProduto(@RequestBody @Valid EstoqueAtualizacaoDto estoqueAtualizacaoDto) {
        return estoqueService.darBaixaProduto(estoqueAtualizacaoDto);
    }

    @PutMapping("/entrada-estoque")
    public ResponseEntity<EstoqueListagemDto> darEntradaProduto(@RequestBody @Valid EstoqueAtualizacaoDto estoqueAtualizacaoDto) {
        return estoqueService.darEntradaProduto(estoqueAtualizacaoDto);
    }

    @GetMapping("trazer-estoque/{lojaId}")
    public ResponseEntity<List<EstoqueListagemDto>> listarEstoquePorLoja(@PathVariable Integer lojaId) {
        return estoqueService.listarEstoquePorLoja(lojaId);
    }
}