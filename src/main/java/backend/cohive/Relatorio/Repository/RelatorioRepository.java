package backend.cohive.Relatorio.Repository;

import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelatorioRepository extends JpaRepository<RelatorioEntidade, Integer> {

}

