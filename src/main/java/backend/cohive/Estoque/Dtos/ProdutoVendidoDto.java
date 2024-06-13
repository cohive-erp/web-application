package backend.cohive.Estoque.Dtos;

import backend.cohive.Estoque.Entidades.Produto;

public class ProdutoVendidoDto {
    private Produto produto;
    private Integer quantidadeVendida;

    public ProdutoVendidoDto(Produto produto, Integer quantidadeVendida) {
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Integer quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }
}
