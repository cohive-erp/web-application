package backend.cohive.Relatorio.Observer;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.ListaObj;
import jakarta.persistence.*;
import org.yaml.snakeyaml.nodes.CollectionNode;

import java.util.Date;
import java.util.List;

public interface Relatorio {
    public void gerarCsv(String nomeArq, List<TransacaoEstoque> transacaoEstoqueLista);
}
