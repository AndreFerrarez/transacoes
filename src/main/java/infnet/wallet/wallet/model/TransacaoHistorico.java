package infnet.wallet.wallet.model;

import jakarta.persistence.*;
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

    // referencia para a transacao original (pode ser null para casos especiais)
    private Long transacaoId;

    private String tipo; // "DEPOSITO" ou "SAQUE"
    private BigDecimal valor;
    private String moeda;

    // operação no histórico: CREATE, UPDATE, DELETE
    private String operacao;

    // quando a operação ocorreu
    private LocalDateTime dataOperacao = LocalDateTime.now();

    public TransacaoHistorico(Long transacaoId, String tipo, BigDecimal valor, String moeda, String operacao) {
        this.transacaoId = transacaoId;
        this.tipo = tipo;
        this.valor = valor;
        this.moeda = moeda;
        this.operacao = operacao;
        this.dataOperacao = LocalDateTime.now();
    }
}