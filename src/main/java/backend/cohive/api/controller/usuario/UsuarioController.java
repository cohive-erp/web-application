package backend.cohive.api.controller.usuario;

import backend.cohive.domain.service.UsuarioService;
import backend.cohive.domain.service.usuario.Usuario;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioLoginDto;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioTokenDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioAtualizacaoDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioCriacaoDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioListagemDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired // Injeção de dep.
    private UsuarioService usuarioService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioListagemDto> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto){
        Usuario usuarioCriado = this.usuarioService.criar(usuarioCriacaoDto);
        UsuarioListagemDto usuarioListagemDto = UsuarioMapper.toUsuarioListagemDto(usuarioCriado);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioListagemDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDTO){
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDTO);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> atualizarUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioAtualizacaoDto usuarioAtualizacaoDto)
            throws ChangeSetPersister.NotFoundException {
        if (usuarioService.existsById(id)) {
            usuarioService.atualizar(id, usuarioAtualizacaoDto);
            UsuarioListagemDto usuarioListagemDto = UsuarioMapper.toUsuarioListagemDto(usuarioService.findById(id));
            return ResponseEntity.ok(usuarioListagemDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id) {
        if (usuarioService.existsById(id)) {
            usuarioService.deleteById(id);
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
