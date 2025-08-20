package infnet.wallet.wallet.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter@Getter
@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // "DEPOSITO" ou "SAQUE"
    private BigDecimal valor;
    private String moeda; // ex: "BTC", "ETH"
    private LocalDateTime data = LocalDateTime.now();

    public Transacao() {
    }

    public Transacao(Long id, String tipo, BigDecimal valor, String moeda, LocalDateTime data) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.moeda = moeda;
        this.data = data;
    }


}
