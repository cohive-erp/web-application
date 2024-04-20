package backend.cohive.Observer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.yaml.snakeyaml.nodes.CollectionNode;

import java.util.Date;
@Entity
public abstract class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date data;
    private String descricao;

    public Relatorio(Date data, String descricao) {
        this.data = data;
        this.descricao = descricao;
    }

    public abstract void gerarRelatorio();

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
}
