package backend.cohive;

import Entidades.Produto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControleEstoque {
    private List<Produto> produtos;

    public ControleEstoque() {
        this.produtos = new ArrayList<>();
    }

    // Adiciona um novo produto ao estoque
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto do estoque
    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    // Retorna o estoque atual de todos os produtos
    public List<Produto> getEstoque() {
        return produtos;
    }

    public Produto obterProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null; // Retorna null se o produto não for encontrado
    }

    // Método para obter todas as categorias de produtos
    public List<String> obterCategorias() {
        Set<String> categoriasSet = new HashSet<>();
        for (Produto produto : produtos) {
            categoriasSet.add(produto.getCategoria());
        }
        return new ArrayList<>(categoriasSet);
    }

    // Método de ordenação de produtos por preço usando Bubble Sort
    public void ordenarProdutosPorPreco(List<Produto> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j).getValor() > lista.get(j + 1).getValor()) {
                    Produto temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }
}
