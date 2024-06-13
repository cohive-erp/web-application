package backend.cohive.Observer.Alerta.Repository;

import backend.cohive.Observer.Alerta.Entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
}
