package backend.cohive.Estoque.Repository;

import backend.cohive.Estoque.Entidades.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, LocalDate> {
    Optional<Estoque> findByDataEntrada(LocalDateTime dataEntrada);

    @Query("SELECT e FROM Estoque e")
    List<Estoque> findAllEstoque();
}
