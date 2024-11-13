package backend.cohive.Estoque.Repository;

import backend.cohive.Estoque.Entidades.Estoque;
import backend.cohive.Loja.Entidades.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, LocalDate> {
    Optional<Estoque> findByDataEntradaInicial(LocalDateTime dataEntradaInicial);

    List<Estoque> findByLoja(Loja loja);
}