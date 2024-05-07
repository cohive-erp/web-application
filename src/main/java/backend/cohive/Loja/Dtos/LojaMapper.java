package backend.cohive.Loja.Dtos;

import backend.cohive.Loja.Entidades.Loja;

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

        return lojaConsultaDto;
    }
}
