package infnet.wallet.wallet.service;

import infnet.wallet.wallet.model.Transacao;
//import infnet.wallet.wallet.model.TransacaoHistorico;
//import infnet.wallet.wallet.repository.TransacaoHistoricoRepository;
import infnet.wallet.wallet.repository.TransacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"
})
public class TransacaoServiceIntegrationTest {

    @Autowired
    private TransacaoService service;

    @Autowired
    private TransacaoRepository transacaoRepository;

//    @Autowired
//    private TransacaoHistoricoRepository historicoRepository;

//    @Test
//    @DisplayName("Ao salvar e excluir, histórico deve ser gravado")
//    void salvarExcluirGeraHistorico() {
//        Transacao t = new Transacao(null, "DEPOSITO", new BigDecimal("123.45"), "BTC", null);
//
//        Transacao saved = service.salvar(t);
//        assertThat(saved.getId()).isNotNull();
//
//        List<TransacaoHistorico> hAfterCreate = historicoRepository.findByTransacaoIdOrderByDataOperacaoDesc(saved.getId());
//        assertThat(hAfterCreate).isNotEmpty();
//        assertThat(hAfterCreate.get(0).getOperacao()).isEqualTo("CREATE");
//
//        // atualizar
//        saved.setValor(new BigDecimal("200"));
//        service.salvar(saved);
//
//        List<TransacaoHistorico> hAfterUpdate = historicoRepository.findByTransacaoIdOrderByDataOperacaoDesc(saved.getId());
//        assertThat(hAfterUpdate).hasSizeGreaterThanOrEqualTo(2);
//        assertThat(hAfterUpdate.get(0).getOperacao()).isEqualTo("UPDATE");
//
//        // excluir
//        service.excluir(saved.getId());
//
//        List<TransacaoHistorico> hAfterDelete = historicoRepository.findByTransacaoIdOrderByDataOperacaoDesc(saved.getId());
//        assertThat(hAfterDelete).extracting("operacao").contains("DELETE");
//    }
}