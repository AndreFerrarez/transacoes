package infnet.wallet.wallet.repository;

import infnet.wallet.wallet.model.Transacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;



@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.mode=never"
})
public class TransacaoRepositoryTest {

    @Autowired
    private TransacaoRepository repository;

    @Test
    @DisplayName("Salvar e buscar por moeda (case insensitive)")
    void salvarEBuscarPorMoeda() {
        Transacao t = new Transacao();
        t.setTipo("SAQUE");
        t.setValor(BigDecimal.valueOf(1000));
        t.setMoeda("BTC");
        t.setData(LocalDateTime.now());

        repository.save(t);

        List<Transacao> resultados = repository.findByMoedaIgnoreCase("btc");

        assertThat(resultados)
                .isNotEmpty()
                .allMatch(tx -> tx.getMoeda().equalsIgnoreCase("BTC"));
    }
}