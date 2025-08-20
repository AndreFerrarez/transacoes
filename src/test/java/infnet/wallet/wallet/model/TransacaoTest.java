package infnet.wallet.wallet.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoTest {

    @Test
    void deveCriarTransacaoComConstrutorSimplificado() {

        LocalDateTime agora = LocalDateTime.now();

        Transacao t = new Transacao(
                1L,
                "DEPOSITO",
                BigDecimal.valueOf(1000),
                "BTC",
                agora
        );

        assertEquals(1L, t.getId());
        assertEquals("DEPOSITO", t.getTipo());
        assertEquals(BigDecimal.valueOf(1000), t.getValor());
        assertEquals("BTC", t.getMoeda());
        assertEquals(agora.withNano(0), t.getData().withNano(0));

    }
}