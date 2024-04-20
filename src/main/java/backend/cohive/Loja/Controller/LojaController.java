package backend.cohive.Loja.Controller;

import backend.cohive.Loja.Dtos.LojaConsultaDto;
import backend.cohive.Loja.Dtos.LojaCriacaoDto;
import backend.cohive.Loja.Dtos.LojaMapper;
import backend.cohive.Loja.Loja;
import backend.cohive.Loja.Repository.LojaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loja")
public class LojaController {
    @Autowired
    private LojaRepository lojaRepository;

    @PostMapping
    public ResponseEntity<LojaConsultaDto> criar(@Valid @RequestBody LojaCriacaoDto lojaCriacaoDto){
        Loja loja = LojaMapper.toEntity(lojaCriacaoDto);
        Loja lojaSalva = lojaRepository.save(loja);
        LojaConsultaDto lojaConsultaDto = LojaMapper.toConsultaDto(loja);

        return ResponseEntity.status(201).body(lojaConsultaDto);
    }
}
