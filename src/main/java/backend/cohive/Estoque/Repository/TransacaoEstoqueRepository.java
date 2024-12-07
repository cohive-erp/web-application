package backend.cohive.Estoque.Repository;

import backend.cohive.Estoque.Dtos.ProdutoVendidoDto;
import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransacaoEstoqueRepository extends JpaRepository<TransacaoEstoque, Integer> {

    @Query("SELECT t FROM TransacaoEstoque t WHERE ((YEAR(t.dataSaida) = :year AND MONTH(t.dataSaida) = :month) OR (YEAR(t.dataEntradaNova) = :year AND MONTH(t.dataEntradaNova) = :month)) AND t.estoque.loja.id = :lojaId")
    List<TransacaoEstoque> findAllByYearAndMonthAndLoja(@Param("year") int year, @Param("month") int month, @Param("lojaId") Integer lojaId);


    @Query("SELECT t.estoque.produto, SUM(t.quantidadeAntesTransacao) as total FROM TransacaoEstoque t WHERE t.estoque.loja.id = :lojaId GROUP BY t.estoque.produto ORDER BY total DESC")
    Page<Object[]> findMostSoldProductByLoja(@Param("lojaId") Integer lojaId, Pageable pageable);


    @Query("SELECT t FROM TransacaoEstoque t WHERE (t.dataSaida BETWEEN :startDate AND :endDate) AND t.tipoTransacao = 'SAIDA' AND t.estoque.loja.id = :lojaId")
    List<TransacaoEstoque> findAllByDateRangeAndLoja(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("lojaId") Integer lojaId);


    @Query("SELECT t.dataSaida, SUM(t.quantidadeAntesTransacao) FROM TransacaoEstoque t WHERE (t.dataSaida BETWEEN :startDate AND :endDate) AND t.estoque.loja.id = :lojaId GROUP BY t.dataSaida")
    List<Object[]> findSalesForLastSevenDaysByLoja(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("lojaId") Integer lojaId);

}
