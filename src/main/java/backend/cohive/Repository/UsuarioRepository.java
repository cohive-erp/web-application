package backend.cohive.Repository;

import backend.cohive.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
