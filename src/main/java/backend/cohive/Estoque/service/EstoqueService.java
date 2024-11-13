package backend.cohive.Estoque.service;

import backend.cohive.Estoque.Dtos.*;
import backend.cohive.Estoque.Entidades.Estoque;
import backend.cohive.Estoque.Entidades.Produto;
import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.EstoqueRepository;
import backend.cohive.Estoque.Repository.ProdutoRepository;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.Loja.Entidades.Loja;
import backend.cohive.Loja.Repository.LojaRepository;
import backend.cohive.Observer.Alerta.Repository.AlertaRepository;
import backend.cohive.Observer.EmailNotifier;
import backend.cohive.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private LojaRepository lojaRepository;
    @Autowired
    private TransacaoEstoqueRepository transacaoEstoqueRepository;
    @Autowired
    private AlertaRepository alertaRepository;
    @Autowired
    private EmailNotifier emailNotifier;
    @Autowired
    private UsuarioService usuarioService;

    public ResponseEntity<ProdutoListagemDto> cadastrarProdutoNovo(ProdutoCriacaoDto produtoCriacaoDto) {
        Optional<Loja> lojaOpt = lojaRepository.findById(produtoCriacaoDto.getLoja().getIdLoja());

        if (lojaOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Loja lojaEncontrada = lojaOpt.get();
        Produto produto = EstoqueProdutoMapper.toProduto(produtoCriacaoDto);
        Optional<Produto> produtoExistenteOpt = produtoRepository.findByNomeAndFabricante(produto.getNome(), produto.getFabricante());

        if (produtoExistenteOpt.isPresent()) {
            return ResponseEntity.status(400).build();
        }

        Produto produtoSalvo = produtoRepository.save(produto);
        ProdutoListagemDto produtoListagemDto = EstoqueProdutoMapper.toProdutoListagemDto(produtoSalvo);

        Estoque estoqueEntrada = new Estoque();
        estoqueEntrada.setDataEntradaInicial(LocalDateTime.now());
        estoqueEntrada.setLoja(lojaEncontrada);
        estoqueEntrada.setProduto(produto);
        estoqueEntrada.setQuantidade(produtoCriacaoDto.getQuantidade());

        estoqueRepository.save(estoqueEntrada);

        return ResponseEntity.status(201).body(produtoListagemDto);
    }

    public ResponseEntity<ProdutoListagemDto> procurarProdutoPorId(Integer id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProdutoListagemDto produtoListagemDto = EstoqueProdutoMapper.toProdutoListagemDto(produto.get());
        return ResponseEntity.ok(produtoListagemDto);
    }

    public ResponseEntity<ProdutoListagemDto> atualizarProduto(Integer id, ProdutoAtualizacaoDto produtoAtualizacaoDto) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Produto produto = produtoOpt.get();
        Produto produtoAtualizado = EstoqueProdutoMapper.toProdutoAtualizacaoDto(produto, produtoAtualizacaoDto);
        Produto produtoSalvo = produtoRepository.save(produtoAtualizado);
        ProdutoListagemDto produtoListagemDto = EstoqueProdutoMapper.toProdutoListagemDto(produtoSalvo);
        return ResponseEntity.ok(produtoListagemDto);
    }

    public ResponseEntity<EstoqueListagemDto> darBaixaProduto(EstoqueAtualizacaoDto estoqueAtualizacaoDto) {
        Optional<Estoque> estoqueOpt = estoqueRepository.findByDataEntradaInicial(estoqueAtualizacaoDto.getDataEntradaInicial());

        if (estoqueOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Estoque estoqueBaixa = EstoqueProdutoMapper.saidaAtualizacaoDto(estoqueAtualizacaoDto);
        estoqueBaixa.setQuantidade(estoqueOpt.get().getQuantidade() - estoqueAtualizacaoDto.getQuantidade());
        if (estoqueBaixa.getQuantidade() < 0) {
            return ResponseEntity.status(400).build(); // Verifica se a quantidade é negativa
        }

        TransacaoEstoque transacaoEstoque = EstoqueProdutoMapper.toTransacaoEstoqueSaida(estoqueBaixa, estoqueOpt);
        estoqueRepository.save(estoqueBaixa);
        transacaoEstoqueRepository.save(transacaoEstoque);

        EstoqueListagemDto estoqueListagemDto = EstoqueProdutoMapper.toEstoqueListagemDto(estoqueBaixa);
        return ResponseEntity.status(200).body(estoqueListagemDto);
    }

    public ResponseEntity<EstoqueListagemDto> darEntradaProduto(EstoqueAtualizacaoDto estoqueAtualizacaoDto) {
        Optional<Estoque> estoqueOpt = estoqueRepository.findByDataEntradaInicial(estoqueAtualizacaoDto.getDataEntradaInicial());

        if (estoqueOpt.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        Estoque estoqueEntrada = EstoqueProdutoMapper.entradaAtualizacaoDto(estoqueAtualizacaoDto);
        estoqueEntrada.setQuantidade(estoqueOpt.get().getQuantidade() + estoqueAtualizacaoDto.getQuantidade());

        TransacaoEstoque transacaoEstoque = EstoqueProdutoMapper.toTransacaoEstoqueEntrada(estoqueEntrada, estoqueOpt);
        estoqueRepository.save(estoqueEntrada);
        transacaoEstoqueRepository.save(transacaoEstoque);

        EstoqueListagemDto estoqueListagemDto = EstoqueProdutoMapper.toEstoqueListagemDto(estoqueEntrada);
        return ResponseEntity.status(200).body(estoqueListagemDto);
    }

    public ResponseEntity<List<EstoqueListagemDto>> listarEstoquePorLoja(@RequestParam Integer lojaId) {
        Optional<Loja> lojaOpt = lojaRepository.findById(lojaId);

        List<Estoque> estoque = estoqueRepository.findByLoja(lojaOpt.get());
        System.out.println("Número de produtos encontrados: " + estoque.size());

        if (estoque.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EstoqueListagemDto> estoqueListagemDtos = EstoqueProdutoMapper.toEstoqueListagemDto(estoque);
        return ResponseEntity.ok(estoqueListagemDtos);
    }

}
