package backend.cohive.domain.service;

import backend.cohive.Loja.Entidades.Loja;
import backend.cohive.Loja.Repository.LojaRepository;
import backend.cohive.api.configuration.security.jwt.GerenciadorTokenJwt;
import backend.cohive.api.configuration.security.jwt.JwtService;
import backend.cohive.domain.Repository.UsuarioRepository;
import backend.cohive.domain.service.usuario.Usuario;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioLoginDto;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioTokenDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioAtualizacaoNumeroDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioCriacaoDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private JwtService jwtService;

    public Usuario criar(UsuarioCriacaoDto usuarioCriacaoDto) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioCriacaoDto.getEmail());

        if (usuarioExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já está em uso");
        }

        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        return this.usuarioRepository.save(novoUsuario);
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Usuario usuarioAutenticado =
                usuarioRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        if (usuarioAutenticado.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário deletado");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Usuario findById(Integer id) throws ChangeSetPersister.NotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public Loja getIdLoja(Integer id) throws ChangeSetPersister.NotFoundException {
        return lojaRepository.findByUsuarioId(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public boolean existsById(Integer id) {
        return usuarioRepository.existsById(id);
    }

    public Usuario atualizarNumero(Integer id, UsuarioAtualizacaoNumeroDto usuarioAtualizacaoDto) throws ChangeSetPersister.NotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Atualizar os campos do usuário com base nos dados do DTO de atualização
        usuario.setNumeroCelular(usuarioAtualizacaoDto.getNumeroCelular());
        // Adicione outras atualizações necessárias

        return usuarioRepository.save(usuario);
    }

    public void setUserAsDeleted(Integer id) throws ChangeSetPersister.NotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        usuario.setDeleted(true);
        usuarioRepository.save(usuario);
    }


    public void sendPasswordResetEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email do usuário não cadastrado"));

        String token = jwtService.createToken(email);
        String resetUrl = "http://localhost:8080/usuarios/validate-token?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Redefinição de Senha");
        message.setText("Clique no link para redefinir sua senha: " + resetUrl);

        mailSender.send(message);
    }

    public boolean validatePasswordResetToken(String token) {
        return jwtService.isTokenValid(token);
    }

    public boolean resetPassword(String token, String newPassword) {
        if (!jwtService.isTokenValid(token)) {
            return false;
        }

        String email = jwtService.getEmailFromToken(token);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);

        return true;
    }
}
