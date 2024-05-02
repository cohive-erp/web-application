package backend.cohive.Estoque.Repository;

import backend.cohive.Estoque.Entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByNomeAndFabricante(String nome, String fabricante);
}
