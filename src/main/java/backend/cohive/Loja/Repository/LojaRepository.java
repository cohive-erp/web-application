package backend.cohive.Loja.Repository;

import backend.cohive.Loja.Entidades.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LojaRepository extends JpaRepository<Loja, Integer> {
    List<Loja> findAllByOrderByCEP();
    Optional<Loja> findByUsuarioId(Integer id);
}
