package backend.cohive.Relatorio.Repository;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Observer.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<RelatorioEntidade, Integer> {

}

