package infnet.wallet.historicoservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transacao_historico")
public class TransacaoHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transacaoId;

    @NotBlank
    private String tipo;          // DEPOSITO | SAQUE

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String moeda;         // BTC | ETH | ...

    @NotBlank
    private String operacao;      // CREATE | UPDATE | DELETE

    private LocalDateTime dataOperacao = LocalDateTime.now();
}