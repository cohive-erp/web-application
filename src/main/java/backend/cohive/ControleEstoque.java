//package backend.cohive;
//
//import backend.cohive.Entidades.Produto;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class ControleEstoque {
//    private List<Produto> estoque;
//
//    public ControleEstoque() {
//        this.estoque = new ArrayList<>();
//    }
//
//    // Adiciona um novo produto ao estoque
//    public void adicionarProduto(Produto produto) {
//        estoque.add(produto);
//    }
//
//    // Remove um produto do estoque
//    public void removerProduto(Produto produto) {
//        estoque.remove(produto);
//    }
//
//    // Retorna o estoque atual de todos os estoque
//    public List<Produto> getEstoque() {
//        return estoque;
//    }
//
//    public int getQuantidadeProduto(String nome) {
//        int quantidade = 0;
//        for (Produto produto : estoque) {
//            if (produto.getNome().equals(nome)) {
//                quantidade++;
//            }
//        }
//        return quantidade;
//    }
//    public int getQuantdadeProdutoPorCategoria(String categoria){
//        int quantidade = 0;
//        for (Produto produto : estoque){
//            if (categoria.equals(produto.getCategoria())){
//                quantidade++;
//            }
//        }
//        return quantidade;
//    }
//
//    public Produto obterProdutoPorId(int id) {
//        for (Produto produto : estoque) {
//            if (produto.getId() == id) {
//                return produto;
//            }
//        }
//        return null; // Retorna null se o produto não for encontrado
//    }
//
//    // Método para obter todas as categorias de estoque
//    public List<String> obterCategorias() {
//        Set<String> categoriasSet = new HashSet<>();
//        for (Produto produto : estoque) {
//            categoriasSet.add(produto.getCategoria());
//        }
//        return new ArrayList<>(categoriasSet);
//    }
//
//    // Método de ordenação de produtos por preço usando Bubble Sort
//    public void ordenarProdutosPorPreco(List<Produto> lista) {
//        int n = lista.size();
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = 0; j < n - i - 1; j++) {
//                if (lista.get(j).getValor() > lista.get(j + 1).getValor()) {
//                    Produto temp = lista.get(j);
//                    lista.set(j, lista.get(j + 1));
//                    lista.set(j + 1, temp);
//                }
//            }
//        }
//    }
//}
