package backend.cohive.Relatorio.Controller;

import backend.cohive.Relatorio.Entidades.RelatorioEntidade;
import backend.cohive.Relatorio.Service.RelatorioService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RelatorioControllerTest {
    @Mock
    private RelatorioService relatorioService;

    @InjectMocks
    private RelatorioController relatorioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Testa geração de relatório de transações CSV com sucesso")
    void testGerarRelatorioTransacoesCSV_Success() {
        String nomeArquivo = "relatorio.csv";

        RelatorioEntidade relatorioEntidadeNovo = new RelatorioEntidade();
        relatorioEntidadeNovo.setIdRelatorio(1);
        relatorioEntidadeNovo.setDescricao("Relatorio de Transacoes");
        relatorioEntidadeNovo.setDataCriacao(LocalDate.now());

        when(relatorioService.criar(any(RelatorioEntidade.class))).thenReturn(relatorioEntidadeNovo);

        ResponseEntity<String> response = relatorioController.gerarRelatorioTransacoesCSV(nomeArquivo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Relatório gerado com sucesso"));
        assertTrue(response.getBody().contains("IdRelatorio: 1"));
    }

    @Test
    @DisplayName("Testa falha ao gerar relatório de transações CSV")
    void testGerarRelatorioTransacoesCSV_Failure() {
        String nomeArquivo = "relatorio.csv";

        doThrow(new RuntimeException("Erro ao gerar CSV")).when(relatorioService).gerarRelatorioTransacoesCSV(anyString());

        ResponseEntity<String> response = relatorioController.gerarRelatorioTransacoesCSV(nomeArquivo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao gerar relatório: Erro ao gerar CSV", response.getBody());
    }

    @Test
    @DisplayName("Testa obtenção de relatório CSV com sucesso")
    Resource testGetRelatorioCSV_Success() throws MalformedURLException {
        String nomeArquivo = "relatorio.csv";
        Resource resource = mock(Resource.class);

        when(resource.getClass().getName()).thenReturn(nomeArquivo);
        when(relatorioService.getRelatorioAsResource(anyString())).thenReturn((org.springframework.core.io.Resource) resource);

        ResponseEntity<org.springframework.core.io.Resource> response = relatorioController.getRelatorioCSV(nomeArquivo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resource, response.getBody());
        assertEquals("attachment; filename=\"relatorio.csv\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        return resource;
    }

    @Test
    @DisplayName("Testa falha ao obter relatório CSV")
    void testGetRelatorioCSV_Failure() throws MalformedURLException {
        String nomeArquivo = "relatorio.csv";

        doThrow(new RuntimeException("Erro ao obter CSV")).when(relatorioService).getRelatorioAsResource(anyString());

        ResponseEntity<org.springframework.core.io.Resource> response = relatorioController.getRelatorioCSV(nomeArquivo);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

//    @Test
//    @DisplayName("Testa geração de relatório mensal CSV com sucesso")
//    void testGerarRelatorioMensalCSV_Success() {
//        String nomeArquivo = "relatorio_mensal.csv";
//        int mes = 6;
//        int ano = 2024;
//
//        RelatorioEntidade relatorioEntidadeNovo = new RelatorioEntidade();
//        relatorioEntidadeNovo.setIdRelatorio(1);
//        relatorioEntidadeNovo.setDescricao("Relatorio Mensal");
//        relatorioEntidadeNovo.setDataCriacao(LocalDate.now());
//
//        when(relatorioService.criar(any(RelatorioEntidade.class))).thenReturn(relatorioEntidadeNovo);
//
//        ResponseEntity<String> response = relatorioController.gerarRelatorioMensalCSV(nomeArquivo, mes, ano, );
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(response.getBody().contains("Relatório gerado com sucesso"));
//        assertTrue(response.getBody().contains("IdRelatorio: 1"));
//    }
}