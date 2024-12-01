package backend.cohive.Loja.Controller;

import backend.cohive.Loja.Dtos.EnderecoDto;
import backend.cohive.Loja.Dtos.LojaConsultaDto;
import backend.cohive.Loja.Dtos.LojaCriacaoDto;
import backend.cohive.Loja.Dtos.LojaMapper;
import backend.cohive.Loja.Entidades.Loja;
import backend.cohive.Loja.Repository.LojaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lojas")
public class LojaController {
    @Autowired
    private LojaRepository lojaRepository;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody LojaCriacaoDto lojaCriacaoDto) {
        // Verifica se o usuário já possui uma loja cadastrada
        Optional<Loja> lojaExistente = lojaRepository.findByUsuarioId(lojaCriacaoDto.getUsuario().getId());
        if (lojaExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro: O usuário já possui uma loja cadastrada.");
        }

        // Busca o endereço pelo CEP
        EnderecoDto enderecoDto = (EnderecoController.buscarEndereco(lojaCriacaoDto.getCEP())).getBody();
        Loja loja = LojaMapper.toEntity(lojaCriacaoDto, enderecoDto);

        // Salva a nova loja
        Loja lojaSalva = lojaRepository.save(loja);
        LojaConsultaDto lojaConsultaDto = LojaMapper.toConsultaDto(loja);

        return ResponseEntity.status(HttpStatus.CREATED).body(lojaConsultaDto);
    }


    @GetMapping("/consulta/{idUser}")
    public ResponseEntity<LojaConsultaDto> buscarPorIdUser(@PathVariable int idUser) {
        Optional<Loja> loja = lojaRepository.findByUsuarioId(idUser);

        if (loja.isPresent()) {
            LojaConsultaDto lojaConsultaDto = LojaMapper.toConsultaDto(loja.get());
            return ResponseEntity.ok(lojaConsultaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
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

//    @PutMapping("/deletar/{id}")
//    ResponseEntity<Void> deletarLoja(@PathVariable int id) {
//        Optional<Loja> optionalLoja = lojaRepository.findById(id);
//        if (!optionalLoja.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//        Loja loja = optionalLoja.get();
//        loja.setDeleted(true);
//        lojaRepository.save(loja);
//
//        return ResponseEntity.noContent().build();
//    }

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

    @GetMapping("/matrizNumero")
    public ResponseEntity<int[][]> getMatrizNumero() {
        // Obter todas as lojas do banco de dados
        List<Loja> lojas = lojaRepository.findAll();

        // Determinar o tamanho da matriz, com base no número de lojas e um número fixo de colunas, por exemplo, 3 colunas
        int colunas = 3; // Número fixo de colunas
        int linhas = (lojas.size() + colunas - 1) / colunas; // Calcula o número de linhas necessário
        int[][] matriz = new int[linhas][colunas];

        // Preencher a matriz com os números das lojas
        for (int i = 0; i < lojas.size(); i++) {
            int linha = i / colunas;
            int coluna = i % colunas;
            matriz[linha][coluna] = lojas.get(i).getNumero(); // Supondo que getNumero() retorna um valor numérico
        }

        // Retornar a resposta HTTP com a matriz de números
        return ResponseEntity.ok(matriz);
    }
}
