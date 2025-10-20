package infnet.wallet.historicoservice.api;

import infnet.wallet.historicoservice.model.TransacaoHistorico;
import infnet.wallet.historicoservice.repository.TransacaoHistoricoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HistoricoControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate rest;

    @Autowired
    TransacaoHistoricoRepository repo;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void deveRegistrarEConsultarPorTransacaoEMoeda() {
        // limpa o banco antes (por segurança)
        repo.deleteAll();

        // 1) POST /historicos (registra CREATE da transacaoId=10, BTC)
        TransacaoHistorico h1 = new TransacaoHistorico();
        h1.setTransacaoId(10L);
        h1.setTipo("DEPOSITO");
        h1.setValor(new BigDecimal("2500.00"));
        h1.setMoeda("BTC");
        h1.setOperacao("CREATE");

        ResponseEntity<TransacaoHistorico> postResp1 =
                rest.postForEntity(url("/historicos"), h1, TransacaoHistorico.class);

        assertThat(postResp1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResp1.getBody()).isNotNull();
        assertThat(postResp1.getBody().getId()).isNotNull();

        // 2) POST /historicos (outra entrada, mesma transacaoId, operacao UPDATE)
        TransacaoHistorico h2 = new TransacaoHistorico();
        h2.setTransacaoId(10L);
        h2.setTipo("DEPOSITO");
        h2.setValor(new BigDecimal("3000.00"));
        h2.setMoeda("BTC");
        h2.setOperacao("UPDATE");

        ResponseEntity<TransacaoHistorico> postResp2 =
                rest.postForEntity(url("/historicos"), h2, TransacaoHistorico.class);

        assertThat(postResp2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // 3) GET /historicos/transacao/10  → deve trazer 2 itens (CREATE, UPDATE)
        ResponseEntity<TransacaoHistorico[]> byTxResp =
                rest.getForEntity(url("/historicos/transacao/10"), TransacaoHistorico[].class);

        assertThat(byTxResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TransacaoHistorico> listaTx = Arrays.asList(byTxResp.getBody());
        assertThat(listaTx).hasSize(2);
        assertThat(listaTx).extracting(TransacaoHistorico::getOperacao)
                .contains("CREATE", "UPDATE");

        // 4) GET /historicos/moeda/BTC  → deve conter os mesmos registros
        ResponseEntity<TransacaoHistorico[]> byCoinResp =
                rest.getForEntity(url("/historicos/moeda/BTC"), TransacaoHistorico[].class);

        assertThat(byCoinResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TransacaoHistorico> listaMoeda = Arrays.asList(byCoinResp.getBody());
        assertThat(listaMoeda).hasSize(2);
        assertThat(listaMoeda).allMatch(it -> "BTC".equalsIgnoreCase(it.getMoeda()));
    }
}