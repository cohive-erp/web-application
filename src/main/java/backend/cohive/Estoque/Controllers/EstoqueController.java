package backend.cohive.Estoque.Controllers;

import backend.cohive.Estoque.Entidades.Estoque;
import backend.cohive.Estoque.Entidades.Produto;
import backend.cohive.Estoque.Dtos.*;
import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Estoque.Repository.EstoqueRepository;
import backend.cohive.Estoque.Repository.ProdutoRepository;
import backend.cohive.Estoque.Repository.TransacaoEstoqueRepository;
import backend.cohive.FilaObj;
import backend.cohive.ListaObj;
import backend.cohive.Loja.Entidades.Loja;
import backend.cohive.Loja.Repository.LojaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @Autowired
    private TransacaoEstoqueRepository transacaoEstoqueRepository;

    // Endpoint para adicionar um novo produto ao estoque
    @PostMapping
    public ResponseEntity<ProdutoListagemDto> cadastrarProdutoNovo(@RequestBody ProdutoCriacaoDto produtoCriacaoDto) {
        Optional<Loja> lojaOpt = lojaRepository.findById(produtoCriacaoDto.getLoja().getIdLoja());

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
            estoqueEntrada.setDataEntradaInicial(LocalDateTime.now());
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
        Optional<Estoque> estoqueOpt = estoqueRepository.findByDataEntradaInicial(estoqueAtualizacaoDto.getDataEntradaInicial());

        if (estoqueOpt.isPresent()){

            Estoque estoqueBaixa = EstoqueProdutoMapper.saidaAtualizacaoDto(estoqueAtualizacaoDto);
            estoqueBaixa.setQuantidade(estoqueOpt.get().getQuantidade() - estoqueAtualizacaoDto.getQuantidade());

            if (estoqueBaixa == null) return ResponseEntity.status(404).build();

            TransacaoEstoque transacaoEstoque = EstoqueProdutoMapper.toTransacaoEstoqueSaida(estoqueBaixa, estoqueOpt);

            estoqueRepository.save(estoqueBaixa);

            transacaoEstoqueRepository.save(transacaoEstoque);

            EstoqueListagemDto estoqueListagemDto = EstoqueProdutoMapper.toEstoqueListagemDto(estoqueBaixa);

            return ResponseEntity.status(200).body(estoqueListagemDto);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/entrada-estoque")
    public ResponseEntity<EstoqueListagemDto> darEntradaProduto(@RequestBody @Valid EstoqueAtualizacaoDto estoqueAtualizacaoDto){
        Optional<Estoque> estoqueOpt = estoqueRepository.findByDataEntradaInicial(estoqueAtualizacaoDto.getDataEntradaInicial());

        if (estoqueOpt.isPresent()){
            Estoque estoqueEntrada = EstoqueProdutoMapper.entradaAtualizacaoDto(estoqueAtualizacaoDto);
            estoqueEntrada.setQuantidade(estoqueOpt.get().getQuantidade() + estoqueAtualizacaoDto.getQuantidade());

            if (estoqueEntrada == null) return ResponseEntity.status(404).build();

            TransacaoEstoque transacaoEstoque = EstoqueProdutoMapper.toTransacaoEstoqueEntrada(estoqueEntrada, estoqueOpt);

            estoqueRepository.save(estoqueEntrada);

            transacaoEstoqueRepository.save(transacaoEstoque);

            EstoqueListagemDto estoqueListagemDto = EstoqueProdutoMapper.toEstoqueListagemDto(estoqueEntrada);

            return ResponseEntity.status(200).body(estoqueListagemDto);
        }
        return ResponseEntity.status(404).build();
    }


    @GetMapping
    public ResponseEntity<List<EstoqueListagemDto>> listarEstoque() {
        List<Estoque> estoque = estoqueRepository.findAllEstoque();

        if (estoque.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<EstoqueListagemDto> estoqueListagemDtos = EstoqueProdutoMapper.toEstoqueListagemDto(estoque);

        return ResponseEntity.ok(estoqueListagemDtos);
    }

    // Método recursivo para encontrar um produto no estoque pelo nome
    @GetMapping("/buscarPorNome/{nome}")
    public ResponseEntity<ProdutoListagemDto> buscarProdutoPorNome(@PathVariable String nome) {
        List<Estoque> estoque = estoqueRepository.findAll();

        if (estoque.isEmpty()) {
            return ResponseEntity.status(404).build(); // Retorna 404 se o estoque estiver vazio
        }

        // Chamada do método auxiliar recursivo para buscar o produto pelo nome
        Optional<ProdutoListagemDto> produtoEncontrado = buscarProdutoPorNomeRecursivo(estoque, nome, 0);

        // Verifica se o produto foi encontrado
        if (produtoEncontrado.isPresent()) {
            return ResponseEntity.status(200).body(produtoEncontrado.get()); // Retorna o produto encontrado
        } else {
            return ResponseEntity.status(404).build(); // Retorna 404 se o produto não for encontrado
        }
    }

    // Método auxiliar recursivo para buscar um produto pelo nome no estoque
    private Optional<ProdutoListagemDto> buscarProdutoPorNomeRecursivo(List<Estoque> estoque, String nome, int index) {
        // Verifica se atingiu o fim da lista de estoque
        if (index >= estoque.size()) {
            return Optional.empty(); // Retorna vazio se o produto não for encontrado
        }

        Estoque estoqueAtual = estoque.get(index);

        // Verifica se o nome do produto no estoque atual corresponde ao nome procurado
        if (estoqueAtual.getProduto().getNome().equalsIgnoreCase(nome)) {
            return Optional.of(EstoqueProdutoMapper.toProdutoListagemDto(estoqueAtual.getProduto())); // Retorna o produto encontrado
        } else {
            // Chama recursivamente o método para verificar o próximo produto no estoque
            return buscarProdutoPorNomeRecursivo(estoque, nome, index + 1);
        }
    }

    @GetMapping("/ordenarProdutos")
    public ResponseEntity<List<ProdutoListagemDto>> ordenarProdutos() {
        // Busca todos os produtos do repositório
        List<Produto> produtosCadastrados = produtoRepository.findAll();

        // Converte a lista de produtos para ListaObj de Produto
        ListaObj<Produto> listaProdutos = new ListaObj<>();
        for (Produto produto : produtosCadastrados) { // Utilize a lista original 'produtosCadastrados'
            listaProdutos.adicionar(produto);
        }

        // Ordena a lista de produtos por preço de venda
        listaProdutos.ordenarPorPrecoVenda();

        // Converter a lista ordenada de Produto para ListaObj de EstoqueListagemDto
        ListaObj<ProdutoListagemDto> produtoListagemDtoListaObj = new ListaObj<>();
        for (Produto produto : listaProdutos.getElementos()) { // Utiliza a lista ordenada 'listaProdutos'
            ProdutoListagemDto produtoListagemDto = EstoqueProdutoMapper.toProdutoListagemDto(produto);
            produtoListagemDtoListaObj.adicionar(produtoListagemDto);
        }

        if (produtosCadastrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        // Retornar a lista ordenada de EstoqueListagemDto
        return ResponseEntity.status(200).body(produtoListagemDtoListaObj.getElementos());
    }

//    public ResponseEntity<EstoqueListagemDto> listarProdutosObj(){
//        Long<Estoque> esqt = estoqueRepository.count();
//    }
//

//
//    // Endpoint para ordenar produtos por preço
//    @GetMapping("/ordenarPorPreco")
//    public ResponseEntity<List<Produto>> ordenarProdutosPorPreco() {
//        List<Produto> estoque = controleEstoque.getEstoque();
//        controleEstoque.ordenarProdutosPorPreco(estoque);
//        return ResponseEntity.status(200).body(estoque);
//    }
}
