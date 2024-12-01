package backend.cohive.Loja.Dtos;

import backend.cohive.Estoque.Dtos.EstoqueProdutoMapper;
import backend.cohive.Estoque.Dtos.ProdutoListagemDto;
import backend.cohive.Estoque.Entidades.Estoque;
import backend.cohive.Loja.Entidades.Loja;

import java.util.List;

public class LojaMapper {
    public static Loja toEntity(LojaCriacaoDto lojaCriacaoDto, EnderecoDto enderecoDto){

        if (lojaCriacaoDto == null){
            return null;
        }

        Loja loja = new Loja();
        loja.setCEP(enderecoDto.getCep());
        loja.setRua(enderecoDto.getRua());
        loja.setBairro(enderecoDto.getBairro());
        loja.setCidade(enderecoDto.getCidade());
        loja.setEstado(enderecoDto.getEstado());
        loja.setCNPJ(lojaCriacaoDto.getCNPJ());
        loja.setNumero(lojaCriacaoDto.getNumero());
        loja.setUsuario(lojaCriacaoDto.getUsuario());

        return loja;
    }

    public static Loja toEntityAtualizacao(LojaCriacaoDto lojaCriacaoDto, EnderecoDto enderecoDto, int id){

        if (lojaCriacaoDto == null){
            return null;
        }

        Loja loja = new Loja();
        loja.setIdLoja(id);
        loja.setCEP(enderecoDto.getCep());
        loja.setRua(enderecoDto.getRua());
        loja.setBairro(enderecoDto.getBairro());
        loja.setCidade(enderecoDto.getCidade());
        loja.setEstado(enderecoDto.getEstado());
        loja.setCNPJ(lojaCriacaoDto.getCNPJ());
        loja.setNumero(lojaCriacaoDto.getNumero());

        return loja;
    }

    public static LojaConsultaDto toConsultaDto(Loja loja){
        if (loja == null){
            return null;
        }

        LojaConsultaDto lojaConsultaDto = new LojaConsultaDto();
        lojaConsultaDto.setIdLoja(loja.getIdLoja());
        lojaConsultaDto.setCEP(loja.getCEP());
        lojaConsultaDto.setRua(loja.getRua());
        lojaConsultaDto.setBairro(loja.getBairro());
        lojaConsultaDto.setCidade(loja.getCidade());
        lojaConsultaDto.setEstado(loja.getEstado());
        lojaConsultaDto.setNumero(loja.getNumero());
        lojaConsultaDto.setCNPJ(loja.getCNPJ());
        lojaConsultaDto.setUsuario(loja.getUsuario());

        return lojaConsultaDto;
    }

    public static List<ProdutoListagemDto> toProdutoListagemDto(List<Estoque> estoque){
        return estoque.stream()
                .map(EstoqueProdutoMapper::toProdutoListagemDto)
                .toList();
    }


    public static LojaConsultaDto toConsultaDto(List<Loja> lojas){
        return (LojaConsultaDto) lojas.stream()
                .map(LojaMapper::toConsultaDto)
                .toList();
    }
}