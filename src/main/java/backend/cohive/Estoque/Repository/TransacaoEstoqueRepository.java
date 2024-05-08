package backend.cohive.Estoque.Repository;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoEstoqueRepository extends JpaRepository<TransacaoEstoque, Integer> {
    @Query("SELECT te FROM TransacaoEstoque te " +
            "WHERE te.dataSaida >= :startOfMonthSaida AND te.dataSaida <= :endOfMonthSaida")
    List<TransacaoEstoque> findByDataSaidaBetween(
            LocalDateTime startOfMonthSaida, LocalDateTime endOfMonthSaida
    );

    @Query("SELECT te FROM TransacaoEstoque te " +
            "WHERE te.dataEntradaNova >= :startOfMonthEntrada AND te.dataEntradaNova <= :endOfMonthEntrada")
    List<TransacaoEstoque> findByDataEntradaNovaBetween(
            LocalDateTime startOfMonthEntrada, LocalDateTime endOfMonthEntrada
    );
}
