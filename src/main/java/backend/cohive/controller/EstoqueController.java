package backend.cohive.controller;

import backend.cohive.entity.Produto;
import backend.cohive.ControleEstoque;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    ControleEstoque controleEstoque = new ControleEstoque();

    // Endpoint para adicionar um novo produto ao estoque
    @PostMapping
    public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto) {
        controleEstoque.adicionarProduto(produto);
        return ResponseEntity.status(200).body(produto);
    }

    // Endpoint para remover um produto do estoque
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable int id) {
        Produto produto = controleEstoque.obterProdutoPorId(id);
        if (produto != null) {
            controleEstoque.removerProduto(produto);
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // Endpoint para obter o estoque atual
    @GetMapping
    public ResponseEntity<List<Produto>> listarEstoque() {
        List<Produto> estoque = controleEstoque.getEstoque();
        return ResponseEntity.status(200).body(estoque);
    }

    // Endpoint para ordenar produtos por preço
    @GetMapping("/ordenarPorPreco")
    public ResponseEntity<List<Produto>> ordenarProdutosPorPreco() {
        List<Produto> estoque = controleEstoque.getEstoque();
        controleEstoque.ordenarProdutosPorPreco(estoque);
        return ResponseEntity.status(200).body(estoque);
    }
}
