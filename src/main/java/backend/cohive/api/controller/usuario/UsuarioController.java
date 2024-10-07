package backend.cohive.api.controller.usuario;

import backend.cohive.FilaObj;
import backend.cohive.Loja.Entidades.Loja;
import backend.cohive.domain.service.UsuarioService;
import backend.cohive.domain.service.usuario.Usuario;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioLoginDto;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioTokenDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioAtualizacaoNumeroDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired // Injeção de dep.
    private UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioListagemDto> criar(@RequestBody @Valid UsuarioCriacaoDto usuarioCriacaoDto) {
        Usuario usuarioCriado = this.usuarioService.criar(usuarioCriacaoDto);
        UsuarioListagemDto usuarioListagemDto = UsuarioMapper.toUsuarioListagemDto(usuarioCriado);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioListagemDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDTO) {
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDTO);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @PutMapping("/atualizar-numero/{id}")
    public ResponseEntity<UsuarioListagemDto> atualizarNumero(@PathVariable Integer id, @RequestBody @Valid UsuarioAtualizacaoNumeroDto usuarioAtualizacaoNumeroDto)
            throws ChangeSetPersister.NotFoundException {
        if (usuarioService.existsById(id)) {
            usuarioService.atualizarNumero(id, usuarioAtualizacaoNumeroDto);
            UsuarioListagemDto usuarioListagemDto = UsuarioMapper.toUsuarioListagemDto(usuarioService.findById(id));
            return ResponseEntity.ok(usuarioListagemDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        usuarioService.sendPasswordResetEmail(email);
        return ResponseEntity.ok("Email de redefinição de senha enviado.");
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        if (usuarioService.validatePasswordResetToken(token)) {
            return ResponseEntity.ok("Token válido. Você pode alterar sua senha.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (usuarioService.resetPassword(token, newPassword)) {
            return ResponseEntity.ok("Senha alterada com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }
    }

    FilaObj<Usuario> filaDeSuporte = new FilaObj<>(10);

    @PostMapping("/fila-suporte")
    public ResponseEntity<String> adicionarUsuarioFilaSuporte(@RequestParam Integer usuarioId) throws ChangeSetPersister.NotFoundException {
        Usuario usuario = usuarioService.findById(usuarioId);
        if (usuario != null) {
            if (filaDeSuporte.isFull()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fila de suporte cheia");
            }
            filaDeSuporte.insert(usuario);
            return ResponseEntity.ok("Usuário adicionado à fila de suporte");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fila-suporte/proximo")
    public ResponseEntity<UsuarioListagemDto> obterProximoUsuarioFilaSuporte() {
        Usuario usuario = filaDeSuporte.poll();
        if (usuario != null) {
            UsuarioListagemDto usuarioListagemDto = UsuarioMapper.toUsuarioListagemDto(usuario);
            return ResponseEntity.ok(usuarioListagemDto);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/fila-suporte")
    public ResponseEntity<List<UsuarioListagemDto>> visualizarFilaSuporte() {
        List<UsuarioListagemDto> usuariosNaFila = new ArrayList<>();
        for (int i = 0; i < filaDeSuporte.getTamanho(); i++) {
            Usuario usuario = filaDeSuporte.peek();
            usuariosNaFila.add(UsuarioMapper.toUsuarioListagemDto(usuario));
            filaDeSuporte.insert(filaDeSuporte.poll()); // Reinsere o usuário para manter a ordem
        }
        return ResponseEntity.ok(usuariosNaFila);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.setUserAsDeleted(id);
            return ResponseEntity.noContent().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

