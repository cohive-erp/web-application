package backend.cohive.Estoque.Controllers;

import backend.cohive.Entidades.Estoque;
import backend.cohive.Entidades.Produto;
import backend.cohive.Estoque.Dtos.*;
import backend.cohive.Estoque.Repository.EstoqueRepository;
import backend.cohive.Estoque.Repository.ProdutoRepository;
import backend.cohive.Loja.Loja;
import backend.cohive.Loja.Repository.LojaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private LojaRepository lojaRepository;

    // Endpoint para adicionar um novo produto ao estoque
    @PostMapping()
    public ResponseEntity<ProdutoListagemDto> cadastrarProdutoNovo(@RequestBody ProdutoCriacaoDto produtoCriacaoDto) {
        Optional<Loja> lojaOpt = lojaRepository.findById(produtoCriacaoDto.getIdLoja());

        if (lojaOpt.isPresent()){
            Loja lojaEncontrada = lojaOpt.get();

            Produto produto = EstoqueProdutoMapper.toProduto(produtoCriacaoDto);
            Optional<Produto> produtoExistenteOpt = produtoRepository.findByNomeAndFabricante(produto.getNome(), produto.getFabricante());

            if (produtoExistenteOpt.isPresent()){
                return ResponseEntity.status(400).build();
            }

            Produto produtoSalvo = produtoRepository.save(produto);
            ProdutoListagemDto produtoListagemDto = EstoqueProdutoMapper.toProdutoListagemDto(produtoSalvo);

            Estoque estoqueEntrada = new Estoque();
            estoqueEntrada.setDataEntrada(LocalDate.now());
            estoqueEntrada.setLoja(lojaEncontrada);
            estoqueEntrada.setProduto(produto);
            estoqueEntrada.setQuantidade(produtoCriacaoDto.getQuantidade());

            Estoque estoqueSalvo = estoqueRepository.save(estoqueEntrada);

            return ResponseEntity.status(201).body(produtoListagemDto);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/baixa-estoque")
    public ResponseEntity<EstoqueListagemDto> darBaixaProduto(@RequestBody @Valid EstoqueAtualizacaoDto estoqueAtualizacaoDto){
        Optional<Estoque> estoqueOpt = estoqueRepository.findByDataEntrada(estoqueAtualizacaoDto.getDataEntrada());

        if (estoqueOpt.isPresent()){
            Estoque estoqueBaixa = EstoqueProdutoMapper.saidaAtualizacaoDto(estoqueAtualizacaoDto);

            if (estoqueBaixa == null) return ResponseEntity.status(404).build();

            estoqueRepository.save(estoqueBaixa);
            EstoqueListagemDto estoqueListagemDto = EstoqueProdutoMapper.toEstoqueListagemDto(estoqueBaixa);
            return ResponseEntity.status(200).body(estoqueListagemDto);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/entrada-estoque")
    public ResponseEntity<EstoqueListagemDto> darEntradaProduto(@RequestBody @Valid EstoqueAtualizacaoDto estoqueAtualizacaoDto){
        Optional<Estoque> estoqueOpt = estoqueRepository.findByDataEntrada(estoqueAtualizacaoDto.getDataEntrada());

        if (estoqueOpt.isPresent()){
            Estoque estoqueEntrada = EstoqueProdutoMapper.entradaAtualizacaoDto(estoqueAtualizacaoDto);

            if (estoqueEntrada == null) return ResponseEntity.status(404).build();

            estoqueRepository.save(estoqueEntrada);
            EstoqueListagemDto estoqueListagemDto = EstoqueProdutoMapper.toEstoqueListagemDto(estoqueEntrada);
            return ResponseEntity.status(200).body(estoqueListagemDto);
        }
        return ResponseEntity.status(404).build();
    }


    // Endpoint para obter o estoque atual
    @GetMapping
    public ResponseEntity<List<EstoqueListagemDto>> listarEstoque() {
        List<Estoque> estoque = estoqueRepository.findAll();

        if (estoque.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<EstoqueListagemDto> estoqueListagemDtos = EstoqueProdutoMapper.toEstoqueListagemDto(estoque);

        return ResponseEntity.status(200).body(estoqueListagemDtos);
    }

//
//    // Endpoint para ordenar produtos por preço
//    @GetMapping("/ordenarPorPreco")
//    public ResponseEntity<List<Produto>> ordenarProdutosPorPreco() {
//        List<Produto> estoque = controleEstoque.getEstoque();
//        controleEstoque.ordenarProdutosPorPreco(estoque);
//        return ResponseEntity.status(200).body(estoque);
//    }
}
