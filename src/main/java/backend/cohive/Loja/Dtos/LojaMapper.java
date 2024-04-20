package backend.cohive.Loja.Dtos;

import backend.cohive.Loja.Loja;

public class LojaMapper {
    public static Loja toEntity(LojaCriacaoDto lojaCriacaoDto){

        if (lojaCriacaoDto == null){
            return null;
        }

        Loja loja = new Loja();
        loja.setRua(lojaCriacaoDto.getRua());
        loja.setBairro(lojaCriacaoDto.getBairro());
        loja.setNumero(lojaCriacaoDto.getNumero());
        loja.setCEP(lojaCriacaoDto.getCEP());
        loja.setCNPJ(lojaCriacaoDto.getCNPJ());

        return loja;
    }

    public static LojaConsultaDto toConsultaDto(Loja loja){
        if (loja == null){
            return null;
        }

        LojaConsultaDto lojaConsultaDto = new LojaConsultaDto();
        lojaConsultaDto.setId(loja.getIdLoja());
        lojaConsultaDto.setRua(loja.getRua());
        lojaConsultaDto.setBairro(loja.getBairro());
        lojaConsultaDto.setNumero(loja.getNumero());
        lojaConsultaDto.setCEP(loja.getCEP());
        lojaConsultaDto.setCNPJ(loja.getCNPJ());

        return lojaConsultaDto;
    }
}
