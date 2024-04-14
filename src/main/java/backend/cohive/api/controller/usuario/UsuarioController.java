package backend.cohive.api.controller.usuario;

import backend.cohive.domain.service.UsuarioService;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioLoginDto;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioTokenDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioCriacaoDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired // Injeção de dep.
    private UsuarioService usuarioService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto){
        this.usuarioService.criar(usuarioCriacaoDto);

        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDTO){
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDTO);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
//
//    @GetMapping
//    public ResponseEntity<List<Usuario>> listarUsuarios(){
//        List<Usuario> usuarios = this.repository.findAll();
//
//        if (usuarios.isEmpty()){
//            return ResponseEntity.status(204).build();
//        }
//
//        return ResponseEntity.status(200).body(usuarios);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
//        // Previnir NPE (NullPointerException)
//        Optional<Usuario> usuarioOpt = this.repository.findById(id);
//
//        return ResponseEntity.of(usuarioOpt);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Usuario> atualizar(
//            @PathVariable Integer id,
//            @RequestBody Usuario usuarioAtualizado
//    ) {
//        if (!this.repository.existsById(id)) {
//            return ResponseEntity.status(404).build();
//        }
//
//        usuarioAtualizado.setId(id);
//        Usuario usuarioSalvo = this.repository.save(usuarioAtualizado);
//        return ResponseEntity.status(200).body(usuarioSalvo);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<List<Usuario>> remover(@PathVariable Integer id){
//        if (!this.repository.existsById(id)) {
//            return ResponseEntity.status(404).build();
//        }
//
//        this.repository.deleteById(id);
//        return ResponseEntity.status(204).build();
//    }
//
//    @GetMapping("/contagem")
//    public ResponseEntity<Long> contar() {
//        long totalUsuarios = (int) this.repository.count();
//        return ResponseEntity.status(200).body(totalUsuarios);
//    }
}
