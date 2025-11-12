package infnet.wallet.wallet.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoCriadaEvent implements Serializable {

    private Long transacaoId;
    private String tipo;
    private BigDecimal valor;
    private String moeda;
    private LocalDateTime dataOperacao;
    private String operacao; // CREATE | UPDATE | DELETE
}