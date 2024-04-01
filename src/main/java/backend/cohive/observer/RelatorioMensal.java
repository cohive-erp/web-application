package backend.cohive.observer;

import backend.cohive.ControleEstoque;
import backend.cohive.entity.Produto;

import java.util.Date;
import java.util.List;

public class RelatorioMensal extends Relatorio {

    public RelatorioMensal(Date data, String descricao, ControleEstoque controleEstoque) {
        super(data, descricao);
        this.controleEstoque = controleEstoque;
    }

    private ControleEstoque controleEstoque = new ControleEstoque();

    @Override
    public void gerarRelatorio() {
        System.out.println("Relatório Mensal - Data: " + getData());
        System.out.println("Descrição: " + getDescricao());

        List<Produto> estoque = controleEstoque.getEstoque();
        controleEstoque.ordenarProdutosPorPreco(estoque);

        System.out.println("Estoque ordenado por preço:");
        for (Produto produto : estoque) {
            System.out.println("Nome: " + produto.getNome() + ", Preço: " + produto.getValor());
        }

        System.out.println("Relatório gerado com sucesso.");
    }
}
