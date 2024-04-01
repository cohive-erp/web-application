package backend.cohive.observer;

import java.util.Date;
import java.util.List;
import backend.cohive.ControleEstoque;
import backend.cohive.entity.Produto;

public class RelatorioMensalCategoria extends Relatorio {

    public RelatorioMensalCategoria(Date data, String descricao, ControleEstoque controleEstoque) {
        super(data, descricao);
        this.controleEstoque = controleEstoque;
    }

    private ControleEstoque controleEstoque = new ControleEstoque();

    @Override
    public void gerarRelatorio() {
        System.out.println("Relatório Mensal por Categoria - Data: " + getData());
        System.out.println("Descrição: " + getDescricao());

        List<Produto> estoque = controleEstoque.getEstoque();

        // Lista para armazenar a quantidade de produtos por categoria
        List<String> categorias = controleEstoque.obterCategorias();

        // Inicializa uma lista para armazenar a quantidade de produtos por categoria
        int[] quantidadePorCategoria = new int[categorias.size()];

        // Calcula a quantidade de produtos por categoria
        for (Produto produto : estoque) {
            String categoria = produto.getCategoria();
            int index = categorias.indexOf(categoria);
            if (index != -1) {
                quantidadePorCategoria[index]++;
            }
        }

        // Exibe as informações do relatório por categoria
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("Categoria: " + categorias.get(i) + ", Quantidade: " + quantidadePorCategoria[i]);
        }

        System.out.println("Relatório gerado com sucesso.");
    }

}
