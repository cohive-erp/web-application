package backend.cohive.Loja.Controller;

import backend.cohive.Loja.Dtos.EnderecoDto;
import backend.cohive.Loja.Dtos.LojaConsultaDto;
import backend.cohive.Loja.Dtos.LojaCriacaoDto;
import backend.cohive.Loja.Dtos.LojaMapper;
import backend.cohive.Loja.Entidades.Loja;
import backend.cohive.Loja.Repository.LojaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lojas")
public class LojaController {
    @Autowired
    private LojaRepository lojaRepository;

    @PostMapping
    public ResponseEntity<LojaConsultaDto> criar(@Valid @RequestBody LojaCriacaoDto lojaCriacaoDto){
        EnderecoDto enderecoDto = (EnderecoController.buscarEndereco(lojaCriacaoDto.getCEP())).getBody();
        Loja loja = LojaMapper.toEntity(lojaCriacaoDto, enderecoDto);
        Loja lojaSalva = lojaRepository.save(loja);
        LojaConsultaDto lojaConsultaDto = LojaMapper.toConsultaDto(loja);

        return ResponseEntity.status(201).body(lojaConsultaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaConsultaDto> atualizarLoja(@PathVariable int id, @Valid @RequestBody LojaCriacaoDto lojaCriacaoDto) {
        // Verifica se a loja com o ID especificado existe no banco de dados
        Optional<Loja> optionalLoja = lojaRepository.findById(id);
        if (!optionalLoja.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Busca o endereço correspondente ao CEP fornecido
        EnderecoDto enderecoDto = EnderecoController.buscarEndereco(lojaCriacaoDto.getCEP()).getBody();

        // Converte o DTO em uma entidade Loja
        Loja lojaAtualizada = LojaMapper.toEntityAtualizacao(lojaCriacaoDto, enderecoDto, id);

        // Salva a loja atualizada no banco de dados
        Loja lojaSalva = lojaRepository.save(lojaAtualizada);

        // Converte a entidade Loja atualizada em um DTO de consulta
        LojaConsultaDto lojaConsultaDto = LojaMapper.toConsultaDto(lojaSalva);

        // Retorna uma resposta de sucesso com o DTO de consulta da loja atualizada
        return ResponseEntity.ok(lojaConsultaDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarLoja(@PathVariable int id) {
        Optional<Loja> optionalLoja = lojaRepository.findById(id);
        if (!optionalLoja.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lojaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Este método será adicionado para procurar uma loja pelo CEP usando pesquisa binária
    @GetMapping("/porCEP")
    public ResponseEntity<LojaConsultaDto> procurarPorCEP(@RequestParam String cep) {
        // Busca a loja pelo CEP usando pesquisa binária
        Loja loja = buscarLojaPorCEP(cep);
        if (loja != null) {
            LojaConsultaDto lojaConsultaDto = LojaMapper.toConsultaDto(loja);
            return ResponseEntity.ok(lojaConsultaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Implementação da pesquisa binária para procurar uma loja pelo CEP
    private Loja buscarLojaPorCEP(String cep) {
        // Primeiro, você precisa obter a lista de lojas ordenadas por CEP
        List<Loja> lojasOrdenadasPorCEP = lojaRepository.findAllByOrderByCEP();

        // Agora, você pode fazer a pesquisa binária na lista ordenada
        int inicio = 0;
        int fim = lojasOrdenadasPorCEP.size() - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            String cepLoja = lojasOrdenadasPorCEP.get(meio).getCEP();
            int comparacao = cep.compareTo(cepLoja);

            if (comparacao == 0) {
                return lojasOrdenadasPorCEP.get(meio); // Loja encontrada
            } else if (comparacao < 0) {
                fim = meio - 1; // Procurar na metade inferior
            } else {
                inicio = meio + 1; // Procurar na metade superior
            }
        }

        return null; // Loja não encontrada
    }

}
