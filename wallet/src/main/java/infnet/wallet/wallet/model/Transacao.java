package infnet.wallet.wallet.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
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

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
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
