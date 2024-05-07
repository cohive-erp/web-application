package backend.cohive.Estoque.Repository;

import backend.cohive.Estoque.Entidades.TransacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoEstoqueRepository extends JpaRepository<TransacaoEstoque, Integer> {
}
