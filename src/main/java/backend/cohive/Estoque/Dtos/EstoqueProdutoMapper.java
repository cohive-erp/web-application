package backend.cohive.Estoque.Dtos;

import backend.cohive.Estoque.Entidades.Estoque;
import backend.cohive.Estoque.Entidades.Produto;

import java.util.ArrayList;
import java.util.List;

public class EstoqueProdutoMapper {

    public static ProdutoListagemDto toProdutoListagemDto(Produto produto){
        if (produto == null) return null;

        ProdutoListagemDto produtoListagemDto = new ProdutoListagemDto();
        produtoListagemDto.setId(produto.getIdProduto());
        produtoListagemDto.setNome(produto.getNome());
        produtoListagemDto.setFabricante(produto.getFabricante());
        produtoListagemDto.setCategoria(produto.getCategoria());
        produtoListagemDto.setPrecoVenda(produto.getPrecoVenda());
        produtoListagemDto.setPrecoCompra(produto.getPrecoCompra());

        return produtoListagemDto;
    }

    public static ProdutoListagemDto toProdutoListagemDto(Estoque estoque){
        if (estoque == null) return null;

        ProdutoListagemDto produtoListagemDto = new ProdutoListagemDto();
        produtoListagemDto.setId(estoque.getProduto().getIdProduto());
        produtoListagemDto.setNome((estoque.getProduto().getNome()));
        produtoListagemDto.setFabricante((estoque.getProduto().getFabricante()));
        produtoListagemDto.setCategoria((estoque.getProduto().getCategoria()));
        produtoListagemDto.setPrecoVenda((estoque.getProduto().getPrecoVenda()));
        produtoListagemDto.setPrecoCompra((estoque.getProduto().getPrecoCompra()));

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
        estoqueListagemDto.setDataEntrada(estoque.getDataEntrada());
        estoqueListagemDto.setLoja(estoque.getLoja());
        estoqueListagemDto.setProduto(estoque.getProduto());
        estoqueListagemDto.setQuantidade(estoque.getQuantidade());

        return estoqueListagemDto;
    }

    public static List<EstoqueListagemDto> toEstoqueListagemDto(List<Estoque> estoqueProdutos) {
        return estoqueProdutos.stream()
                .map(estoque -> toEstoqueListagemDto(estoque))
                .toList();
    }
    public static Produto toProduto(ProdutoCriacaoDto produtoCriacaoDto){

        if (produtoCriacaoDto == null) return null;

        Produto produto = new Produto();

        produto.setNome(produtoCriacaoDto.getNome());
        produto.setFabricante(produtoCriacaoDto.getFabricante());
        produto.setCategoria(produtoCriacaoDto.getCategoria());
        produto.setPrecoVenda(produtoCriacaoDto.getPrecoVenda());
        produto.setPrecoCompra(produtoCriacaoDto.getPrecoCompra());

        return produto;
    }

    public static Estoque saidaAtualizacaoDto(EstoqueAtualizacaoDto estoqueAtualizacaoDto){
        if (estoqueAtualizacaoDto.getQuantidade() > 0){
            Estoque estoqueNovo = new Estoque();
            estoqueNovo.setDataEntrada(estoqueAtualizacaoDto.getDataEntrada());
            estoqueNovo.setLoja(estoqueAtualizacaoDto.getLoja());
            estoqueNovo.setProduto(estoqueAtualizacaoDto.getProduto());
            estoqueNovo.setQuantidade(estoqueAtualizacaoDto.getQuantidade());

            return estoqueNovo;
        }
        return null;
    }

    public static Estoque entradaAtualizacaoDto(EstoqueAtualizacaoDto estoqueAtualizacaoDto) {
            Estoque estoqueNovo = new Estoque();
            estoqueNovo.setDataEntrada(estoqueAtualizacaoDto.getDataEntrada());
            estoqueNovo.setLoja(estoqueAtualizacaoDto.getLoja());
            estoqueNovo.setProduto(estoqueAtualizacaoDto.getProduto());
            estoqueNovo.setQuantidade(estoqueAtualizacaoDto.getQuantidade());

            return estoqueNovo;
    }

}
