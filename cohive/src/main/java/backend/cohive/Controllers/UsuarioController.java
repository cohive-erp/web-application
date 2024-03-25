package backend.cohive.Controllers;

import backend.cohive.Entidades.Usuario;
import backend.cohive.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired // Injeção de dep.
    private UsuarioRepository repository;

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
        Usuario usuarioSalvo = this.repository.save(usuario);

        return ResponseEntity.status(200).body(usuarioSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = this.repository.findAll();

        if (usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        // Previnir NPE (NullPointerException)
        Optional<Usuario> usuarioOpt = this.repository.findById(id);

        return ResponseEntity.of(usuarioOpt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable int indice,
            @RequestBody Usuario usuarioAtualizado
    ) {
        List<Usuario> usuarios = this.repository.findAll();

        if (isIndiceValid(indice)){
            usuarios.set(indice, usuarioAtualizado);
            return ResponseEntity.status(201).body(usuarioAtualizado);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{indice}")
    public ResponseEntity<List<Usuario>> removerPorIndice(@PathVariable int indice){
        List<Usuario> usuarios = this.repository.findAll();

        if (isIndiceValid(indice)){
            usuarios.remove(indice);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }


    private boolean isIndiceValid(int indice){
        List<Usuario> usuarios = this.repository.findAll();
        return indice >= 0 && indice < usuarios.size();
    }

}
