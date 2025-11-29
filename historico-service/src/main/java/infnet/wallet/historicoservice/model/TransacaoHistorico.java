package infnet.wallet.historicoservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "historico")
public class TransacaoHistorico {

    @Id
    private String id; //padrão do Mongo

    private Long transacaoIdOriginal; // Guardamos o ID que veio da Wallet
    private String tipo;
    private BigDecimal valor;
    private String moeda;
    private String operacao;
    private LocalDateTime dataOperacao;
}