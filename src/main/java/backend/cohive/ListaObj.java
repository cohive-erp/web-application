package backend.cohive;

import backend.cohive.Estoque.Entidades.Produto;

import java.util.ArrayList;
import java.util.List;

public class ListaObj<T> {
    private List<T> elementos;

    public ListaObj() {
        this.elementos = new ArrayList<>();
    }

    public void setElemento(int indice, T elemento) {
        elementos.set(indice, elemento);
    }

    public int getTamanho() {
        return elementos.size();
    }

    public T getElemento(int indice) {
        return elementos.get(indice);
    }

    public List<T> getElementos() {
        return elementos;
    }

    public void setLista(List<T> lista) {
        this.elementos = lista;
    }
    public void ordenarPorPrecoVenda() {
        int n = getTamanho();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (getPrecoVenda(elementos.get(j)) > getPrecoVenda(elementos.get(j + 1))) {
                    T temp = elementos.get(j);
                    elementos.set(j, elementos.get(j + 1));
                    elementos.set(j + 1, temp);
                }
            }
        }
    }

    public void adicionar(T elemento) {
        elementos.add(elemento);
    }

    private double getPrecoVenda(T elemento) {
        if (elemento instanceof Produto) {
            return ((Produto) elemento).getPrecoVenda();
        }
        // Implemente para outros tipos de objetos, se necessário
        return 0.0;
    }

    public int pesquisaBinariaPorPrecoVenda(double precoVenda) {
        int esquerda = 0;
        int direita = getTamanho() - 1;

        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            double precoMeio = getPrecoVenda(getElemento(meio));

            if (precoMeio == precoVenda) {
                return meio;
            } else if (precoMeio < precoVenda) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return -1; // Não encontrado
    }
}
