package backend.cohive.domain.service.usuario.dtos;

import backend.cohive.domain.service.usuario.Usuario;
import backend.cohive.domain.service.usuario.autenticacao.dto.UsuarioTokenDto;

public class UsuarioMapper {
    public static Usuario of(UsuarioCriacaoDto usuarioCriacaoDto){
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setNumeroCelular(usuarioCriacaoDto.getNumeroCelular());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setSenha(usuarioCriacaoDto.getSenha());

        return usuario;
    }

    public static UsuarioListagemDto toUsuarioListagemDto(Usuario usuario){
        UsuarioListagemDto usuarioListagemDto = new UsuarioListagemDto();

        usuarioListagemDto.setId(usuario.getId());
        usuarioListagemDto.setNome(usuario.getNome());
        usuarioListagemDto.setNumeroCelular(usuario.getNumeroCelular());
        usuarioListagemDto.setEmail(usuario.getEmail());
        usuarioListagemDto.setSenha(usuario.getSenha());

        return usuarioListagemDto;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}
