package backend.cohive.Observer;

import java.util.Date;
import java.util.List;
import backend.cohive.ControleEstoque;
import backend.cohive.Entidades.Produto;

public class RelatorioMensalCategoria implements Relatorio {
    private Date data;
    private String descricao;
    private ControleEstoque controleEstoque;

    public RelatorioMensalCategoria(Date data, String descricao, ControleEstoque controleEstoque) {
        this.data = data;
        this.descricao = descricao;
        this.controleEstoque = controleEstoque;
    }

    @Override
    public void gerarRelatorio() {
        System.out.println("Relatório Mensal por Categoria - Data: " + data);
        System.out.println("Descrição: " + descricao);

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

    public ControleEstoque getControleEstoque() {
        return controleEstoque;
    }

    public void setControleEstoque(ControleEstoque controleEstoque) {
        this.controleEstoque = controleEstoque;
    }
}
