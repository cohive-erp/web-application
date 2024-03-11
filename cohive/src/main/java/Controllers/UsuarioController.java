package Controllers;

import Entidades.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {
    private List<Usuario> usuarios = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Usuario> cadastar(@RequestBody Usuario usuarioNovo){
        if (usuarioNovo.getNome() == null) {
            return ResponseEntity.status(404).build();
        }
        usuarios.add(usuarioNovo);

        return ResponseEntity.status(200).body(usuarioNovo);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        if (usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarios);
    }

    @PutMapping("/{indice}")
    public ResponseEntity<Usuario> atualizar(@PathVariable int indice,
                                             @RequestBody Usuario usuarioAtualizado){
        if (isIndiceValid(indice)){
            usuarios.set(indice, usuarioAtualizado);
            return ResponseEntity.status(201).body(usuarioAtualizado);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{indice}")
    public ResponseEntity<List<Usuario>> removerPorIndice(@PathVariable int indice){
        if (isIndiceValid(indice)){
            usuarios.remove(indice);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }


    private boolean isIndiceValid(int indice){
        return indice >= 0 && indice < usuarios.size();
    }

}
