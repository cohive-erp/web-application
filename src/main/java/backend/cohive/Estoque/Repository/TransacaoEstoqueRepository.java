package backend.cohive.Estoque.Repository;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoEstoqueRepository extends JpaRepository<TransacaoEstoque, Integer> {
    @Query("SELECT t FROM TransacaoEstoque t WHERE (YEAR(t.dataSaida) = :year AND MONTH(t.dataSaida) = :month) OR (YEAR(t.dataEntradaNova) = :year AND MONTH(t.dataEntradaNova) = :month)")
    List<TransacaoEstoque> findAllByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
