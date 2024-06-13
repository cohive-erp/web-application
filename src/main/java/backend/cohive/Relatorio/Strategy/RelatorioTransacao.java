package backend.cohive.Relatorio.Strategy;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;

public class RelatorioTransacao implements Relatorio {
    @Override
    public void gerarCsv(String nomeArq, List<TransacaoEstoque> transacaoEstoqueLista) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false; // Definindo deuRuim como false inicialmente

        nomeArq += ".csv";

        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }
        try {
            for (int i = 0; i < transacaoEstoqueLista.size() ; i++) {
                TransacaoEstoque transacaoEstoque = transacaoEstoqueLista.get(i);
                saida.format("%s; %s; %d; %s; %s; %s; %.2f; %.2f\n",
                        transacaoEstoque.getDataSaida(),
                        transacaoEstoque.getDataEntradaNova(),
                        transacaoEstoque.getQuantidadeAntesTransacao(),
                        transacaoEstoque.getEstoque().getProduto().getNome(),
                        transacaoEstoque.getEstoque().getProduto().getFabricante(),
                        transacaoEstoque.getEstoque().getProduto().getCategoria(),
                        transacaoEstoque.getEstoque().getProduto().getPrecoVenda(),
                        transacaoEstoque.getEstoque().getProduto().getPrecoCompra());
            }
        } catch (FormatterClosedException erro) {
            System.out.printf("Erro ao gravar o arquivo");
            erro.printStackTrace();
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.printf("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
    }
}
