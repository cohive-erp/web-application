package backend.cohive.api.controller.usuario;

import backend.cohive.FilaObj;
import backend.cohive.domain.service.UsuarioService;
import backend.cohive.domain.service.usuario.Usuario;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioLoginDto;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioTokenDto;
import backend.cohive.domain.service.usuario.dtos.*;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private FilaObj<Usuario> filaDeSuporte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filaDeSuporte = new FilaObj<>(10);
        usuarioController.filaDeSuporte = filaDeSuporte; // Ensure the controller uses the mock
    }

    @Test
    @DisplayName("Testa solicitação de redefinição de senha")
    void testRequestPasswordReset() {
        String email = "test@example.com";

        // Using doNothing with a void method
        doNothing().when(usuarioService).sendPasswordResetEmail(email);

        ResponseEntity<String> response = usuarioController.requestPasswordReset(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email de redefinição de senha enviado.", response.getBody());
    }

    @Test
    @DisplayName("Testa validação de token com sucesso")
    void testValidateToken_Success() {
        String token = "valid-token";

        when(usuarioService.validatePasswordResetToken(token)).thenReturn(true);

        ResponseEntity<String> response = usuarioController.validateToken(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token válido. Você pode alterar sua senha.", response.getBody());
    }

    @Test
    @DisplayName("Testa redefinição de senha com sucesso")
    void testResetPassword_Success() {
        String token = "valid-token";
        String newPassword = "newPassword123";

        when(usuarioService.resetPassword(token, newPassword)).thenReturn(true);

        ResponseEntity<String> response = usuarioController.resetPassword(token, newPassword);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Senha alterada com sucesso.", response.getBody());
    }

    @Test
    @DisplayName("Testa adicionar usuário à fila de suporte com sucesso")
    void testAdicionarUsuarioFilaSuporte_Success() throws ChangeSetPersister.NotFoundException {
        Integer usuarioId = 1;
        Usuario usuario = mock(Usuario.class);

        when(usuarioService.findById(usuarioId)).thenReturn(usuario);

        ResponseEntity<String> response = usuarioController.adicionarUsuarioFilaSuporte(usuarioId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário adicionado à fila de suporte", response.getBody());
    }

    @Test
    @DisplayName("Testa exclusão de usuário com sucesso")
    void testDeleteUsuario_Success() throws ChangeSetPersister.NotFoundException {
        Integer id = 1;

        // Using doNothing with a void method
        doNothing().when(usuarioService).setUserAsDeleted(id);

        ResponseEntity<Void> response = usuarioController.deleteUsuario(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Testa exclusão de usuário que não existe")
    void testDeleteUsuario_NotFound() throws ChangeSetPersister.NotFoundException {
        Integer id = 1;

        doThrow(ChangeSetPersister.NotFoundException.class).when(usuarioService).setUserAsDeleted(id);

        ResponseEntity<Void> response = usuarioController.deleteUsuario(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
