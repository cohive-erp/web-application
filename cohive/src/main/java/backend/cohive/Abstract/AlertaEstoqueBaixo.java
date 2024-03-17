package backend.cohive.Abstract;

import backend.cohive.ControleEstoque;
import backend.cohive.Entidades.Produto;

import java.time.LocalDateTime;
import java.util.List;

public class AlertaEstoqueBaixo extends Alerta {

    private ControleEstoque controleEstoque = new ControleEstoque();

    public AlertaEstoqueBaixo(String tipo, LocalDateTime data, String mensagem, ControleEstoque controleEstoque) {
        super(tipo, data, mensagem);
        this.controleEstoque = controleEstoque;
    }

    @Override
    public void gerarAlerta() {
            for (Produto produto : controleEstoque.getEstoque()) {
                if (controleEstoque.getQuantidadeProduto(produto.getNome()) < 5) {
                    System.out.println("Alerta: Estoque baixo para o produto " + produto.getNome());
                }
            }
    }
}
