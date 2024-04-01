package backend.cohive.Abstract;

import backend.cohive.ControleEstoque;
import backend.cohive.Entidades.Produto;

import java.time.LocalDateTime;

public class AlertaEstoqueZerado extends Alerta {

    private ControleEstoque controleEstoque = new ControleEstoque();

    public AlertaEstoqueZerado(String tipo, LocalDateTime data, String mensagem, ControleEstoque controleEstoque) {
        super(tipo, data, mensagem);
        this.controleEstoque = controleEstoque;
    }

    @Override
    public void gerarAlerta() {
        for (Produto produto : controleEstoque.getEstoque()) {
            if (controleEstoque.getQuantidadeProduto(produto.getNome()) == 0) {
                System.out.println("Alerta: Estoque zerado para o produto " + produto.getNome());
            }
        }
    }
}
