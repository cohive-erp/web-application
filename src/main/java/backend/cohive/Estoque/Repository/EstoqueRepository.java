package backend.cohive.Estoque.Repository;

import backend.cohive.Entidades.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    Optional<Estoque> findByDataEntrada(LocalDate dataEntrada);
}
