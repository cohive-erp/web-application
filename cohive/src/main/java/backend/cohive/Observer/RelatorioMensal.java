package backend.cohive.Observer;

import backend.cohive.ControleEstoque;
import backend.cohive.Entidades.Produto;

import java.util.Date;
import java.util.List;

public class RelatorioMensal implements Relatorio {
    private Date data;
    private String descricao;

    ControleEstoque controleEstoque = new ControleEstoque();

    public RelatorioMensal(Date data, String descricao) {
        this.data = data;
        this.descricao = descricao;
    }

    @Override
    public void gerarRelatorio() {
        System.out.println("Relatório Mensal - Data: " + data);
        System.out.println("Descrição: " + descricao);

        List<Produto> estoque = controleEstoque.getEstoque();
        controleEstoque.ordenarProdutosPorPreco(estoque);

        System.out.println("Estoque ordenado por preço:");
        for (Produto produto : estoque) {
            System.out.println("Nome: " + produto.getNome() + ", Preço: " + produto.getValor());
        }

        System.out.println("Relatório gerado com sucesso.");
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
