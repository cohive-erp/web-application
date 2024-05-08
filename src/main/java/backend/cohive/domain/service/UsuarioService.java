package backend.cohive.domain.service;

import backend.cohive.api.configuration.security.jwt.GerenciadorTokenJwt;
import backend.cohive.domain.Repository.UsuarioRepository;
import backend.cohive.domain.service.usuario.Usuario;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioLoginDto;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioTokenDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioAtualizacaoDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioCriacaoDto;
import backend.cohive.domain.service.usuario.dtos.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Usuario criar(UsuarioCriacaoDto usuarioCriacaoDto) {
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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

    public Usuario findById(Integer id) throws ChangeSetPersister.NotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public boolean existsById(Integer id) {
        return usuarioRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Integer id, UsuarioAtualizacaoDto usuarioAtualizacaoDto) throws ChangeSetPersister.NotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Atualizar os campos do usuário com base nos dados do DTO de atualização
        usuario.setNome(usuarioAtualizacaoDto.getNome());
        usuario.setEmail(usuarioAtualizacaoDto.getEmail());
        // Adicione outras atualizações necessárias

        return usuarioRepository.save(usuario);
    }
}
