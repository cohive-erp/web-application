package backend.cohive.Estoque.Dtos;

import backend.cohive.Estoque.Entidades.Estoque;
import backend.cohive.Estoque.Entidades.Produto;
import backend.cohive.Estoque.Entidades.TransacaoEstoque;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstoqueProdutoMapper {

    public static ProdutoListagemDto toProdutoListagemDto(Produto produto){
        if (produto == null) return null;

        ProdutoListagemDto produtoListagemDto = new ProdutoListagemDto();
        produtoListagemDto.setId(produto.getIdProduto());
        produtoListagemDto.setNome(produto.getNome());
        produtoListagemDto.setFabricante(produto.getFabricante());
        produtoListagemDto.setCategoria(produto.getCategoria());
        produtoListagemDto.setDescricao(produto.getDescricao());
        produtoListagemDto.setPrecoVenda(produto.getPrecoVenda());
        produtoListagemDto.setPrecoCompra(produto.getPrecoCompra());
        produtoListagemDto.setLoja(produto.getLoja());

        return produtoListagemDto;
    }

    public static ProdutoListagemDto toProdutoListagemDto(Estoque estoque){
        if (estoque == null) return null;

        ProdutoListagemDto produtoListagemDto = new ProdutoListagemDto();
        produtoListagemDto.setId(estoque.getProduto().getIdProduto());
        produtoListagemDto.setNome((estoque.getProduto().getNome()));
        produtoListagemDto.setFabricante((estoque.getProduto().getFabricante()));
        produtoListagemDto.setCategoria((estoque.getProduto().getCategoria()));
        produtoListagemDto.setDescricao((estoque.getProduto().getDescricao()));
        produtoListagemDto.setPrecoVenda((estoque.getProduto().getPrecoVenda()));
        produtoListagemDto.setPrecoCompra((estoque.getProduto().getPrecoCompra()));
        produtoListagemDto.setLoja(estoque.getLoja());

        return produtoListagemDto;
    }

    public static List<ProdutoListagemDto> toProdutoListagemDto(List<Estoque> estoque){
        return estoque.stream()
                .map(EstoqueProdutoMapper::toProdutoListagemDto)
                .toList();
    }

    public static EstoqueListagemDto toEstoqueListagemDto(Estoque estoque) {
        if (estoque == null) return null;

        EstoqueListagemDto estoqueListagemDto = new EstoqueListagemDto();
        estoqueListagemDto.setDataEntradaInicial(estoque.getDataEntradaInicial());
        estoqueListagemDto.setLoja(estoque.getLoja());
        estoqueListagemDto.setProduto(estoque.getProduto());
        estoqueListagemDto.setQuantidade(estoque.getQuantidade());

        return estoqueListagemDto;
    }

    public static List<EstoqueListagemDto> toEstoqueListagemDto(List<Estoque> estoqueProdutos) {
        if (estoqueProdutos == null || estoqueProdutos.isEmpty()) {
            return Collections.emptyList();
        }

        return estoqueProdutos.stream()
                .map(EstoqueProdutoMapper::toEstoqueListagemDto)
                .collect(Collectors.toList());
    }
    public static Produto toProduto(ProdutoCriacaoDto produtoCriacaoDto){

        if (produtoCriacaoDto == null) return null;

        Produto produto = new Produto();

        produto.setNome(produtoCriacaoDto.getNome());
        produto.setFabricante(produtoCriacaoDto.getFabricante());
        produto.setCategoria(produtoCriacaoDto.getCategoria());
        produto.setDescricao(produtoCriacaoDto.getDescricao());
        produto.setPrecoVenda(produtoCriacaoDto.getPrecoVenda());
        produto.setPrecoCompra(produtoCriacaoDto.getPrecoCompra());
        produto.setLoja(produtoCriacaoDto.getLoja());

        return produto;
    }

    public static Estoque saidaAtualizacaoDto(EstoqueAtualizacaoDto estoqueAtualizacaoDto){
        if (estoqueAtualizacaoDto.getQuantidade() > 0){
            Estoque estoqueNovo = new Estoque();
            estoqueNovo.setDataEntradaInicial(estoqueAtualizacaoDto.getDataEntradaInicial());
            estoqueNovo.setLoja(estoqueAtualizacaoDto.getLoja());
            estoqueNovo.setProduto(estoqueAtualizacaoDto.getProduto());
            estoqueNovo.setQuantidade(estoqueAtualizacaoDto.getQuantidade());

            return estoqueNovo;
        }
        return null;
    }

    public static TransacaoEstoque toTransacaoEstoqueSaida(Estoque estoque, Optional<Estoque> estoqueOpt){
        if (estoque == null) return null;

        TransacaoEstoque transacaoEstoque = new TransacaoEstoque();
        transacaoEstoque.setDataSaida(LocalDateTime.now());
        transacaoEstoque.setQuantidadeAntesTransacao(estoqueOpt.get().getQuantidade());
        transacaoEstoque.setEstoque(estoque);
        transacaoEstoque.setTipoTransacao("SAIDA");

        return transacaoEstoque;
    }

    public static TransacaoEstoque toTransacaoEstoqueEntrada(Estoque estoque, Optional<Estoque> estoqueOpt){
        if (estoque == null) return null;

        TransacaoEstoque transacaoEstoque = new TransacaoEstoque();
        transacaoEstoque.setDataEntradaNova(LocalDateTime.now());
        transacaoEstoque.setQuantidadeAntesTransacao(estoqueOpt.get().getQuantidade());
        transacaoEstoque.setEstoque(estoque);
        transacaoEstoque.setTipoTransacao("ENTRADA");

        return transacaoEstoque;
    }
    public static Estoque entradaAtualizacaoDto(EstoqueAtualizacaoDto estoqueAtualizacaoDto) {
            Estoque estoqueNovo = new Estoque();
            estoqueNovo.setDataEntradaInicial(estoqueAtualizacaoDto.getDataEntradaInicial());
            estoqueNovo.setLoja(estoqueAtualizacaoDto.getLoja());
            estoqueNovo.setProduto(estoqueAtualizacaoDto.getProduto());
            estoqueNovo.setQuantidade(estoqueAtualizacaoDto.getQuantidade());

            return estoqueNovo;
    }

    public static Produto toProdutoAtualizacaoDto(Produto produto, ProdutoAtualizacaoDto produtoAtualizacaoDto) {
        if (produto == null || produtoAtualizacaoDto == null) return null;

        produto.setNome(produtoAtualizacaoDto.getNome());
        produto.setFabricante(produtoAtualizacaoDto.getFabricante());
        produto.setCategoria(produtoAtualizacaoDto.getCategoria());
        produto.setDescricao(produtoAtualizacaoDto.getDescricao());
        produto.setPrecoVenda(produtoAtualizacaoDto.getPrecoVenda());
        produto.setPrecoCompra(produtoAtualizacaoDto.getPrecoCompra());
        produto.setQuantidade(produtoAtualizacaoDto.getQuantidade());
        produto.setLoja(produto.getLoja());

        return produto;
    }

}
