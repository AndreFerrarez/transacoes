package infnet.wallet.wallet.service;

import infnet.wallet.wallet.historico.HistoricoPort;
import infnet.wallet.wallet.model.Transacao;
import infnet.wallet.wallet.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class TransacaoServiceTest {

    @Autowired
    TransacaoService service;

    @Autowired
    TransacaoRepository transacaoRepository;

    @MockitoBean
    HistoricoPort historicoPort;

    @BeforeEach
    void setup() {
        transacaoRepository.deleteAll();
        reset(historicoPort);
    }

    @Test
    void deveSalvarTransacaoNova_eRegistrarHistoricoCreate() {
        Transacao nova = new Transacao();
        nova.setTipo("DEPOSITO");
        nova.setValor(new BigDecimal("1500.00"));
        nova.setMoeda("BTC");

        Transacao salva = service.salvar(nova);

        assertThat(salva.getId()).isNotNull();

        // verifica chamada ao histórico com "CREATE"
        verify(historicoPort, times(1)).registrarHistorico(
                eq(salva.getId()),
                eq("DEPOSITO"),
                eq(new BigDecimal("1500.00")),
                eq("BTC"),
                eq("CREATE")
        );
    }

    @Test
    void deveAtualizarTransacaoExistente_eRegistrarHistoricoUpdate() {
        // cria uma existente
        Transacao t = new Transacao();
        t.setTipo("DEPOSITO");
        t.setValor(new BigDecimal("100.00"));
        t.setMoeda("ETH");
        t = transacaoRepository.save(t);

        // altera e salva novamente via service (deve ser UPDATE)
        t.setValor(new BigDecimal("200.00"));
        Transacao atualizada = service.salvar(t);

        assertThat(atualizada.getValor()).isEqualByComparingTo("200.00");

        verify(historicoPort, times(1)).registrarHistorico(
                eq(atualizada.getId()),
                eq("DEPOSITO"),
                eq(new BigDecimal("200.00")),
                eq("ETH"),
                eq("UPDATE")
        );
    }

    @Test
    void deveExcluirTransacao_eRegistrarHistoricoDelete() {
        Transacao t = new Transacao();
        t.setTipo("SAQUE");
        t.setValor(new BigDecimal("50.00"));
        t.setMoeda("BTC");
        t = transacaoRepository.save(t);

        Long id = t.getId();
        service.excluir(id);

        // repo não deve mais encontrar a transação
        Optional<Transacao> byId = transacaoRepository.findById(id);
        assertThat(byId).isEmpty();

        // histórico chamado com DELETE e valores originais
        verify(historicoPort, times(1)).registrarHistorico(
                eq(id),
                eq("SAQUE"),
                eq(new BigDecimal("50.00")),
                eq("BTC"),
                eq("DELETE")
        );
    }

    @Test
    void deveCalcularSaldoPorMoeda() {
        // BTC: +1000 (depósito) - 200 (saque) = 800
        Transacao d1 = new Transacao();
        d1.setTipo("DEPOSITO");
        d1.setValor(new BigDecimal("1000.00"));
        d1.setMoeda("BTC");
        transacaoRepository.save(d1);

        Transacao s1 = new Transacao();
        s1.setTipo("SAQUE");
        s1.setValor(new BigDecimal("200.00"));
        s1.setMoeda("BTC");
        transacaoRepository.save(s1);

        // Outra moeda para garantir filtro
        Transacao d2 = new Transacao();
        d2.setTipo("DEPOSITO");
        d2.setValor(new BigDecimal("999.00"));
        d2.setMoeda("ETH");
        transacaoRepository.save(d2);

        BigDecimal saldoBtc = service.calcularSaldo("BTC");
        assertThat(saldoBtc).isEqualByComparingTo("800.00");

        // nenhuma chamada ao histórico nesse método
        verifyNoInteractions(historicoPort);
    }
}