package infnet.wallet.wallet.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class HistoricoDTO {

    private Long id;
    private Long transacaoId;
    private String tipo;        // DEPOSITO | SAQUE
    private BigDecimal valor;
    private String moeda;       // BTC | ETH | ...
    private String operacao;    // CREATE | UPDATE | DELETE
    private LocalDateTime dataOperacao;

}
