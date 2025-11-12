package infnet.wallet.wallet.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransacaoEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.transacoes}")
    private String exchangeName;

    @Value("${rabbitmq.routingkey.transacoes}")
    private String routingKey;

    public TransacaoEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarEvento(TransacaoCriadaEvent evento) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, evento);
        System.out.printf(
                "[RabbitMQ] Evento publicado: %s %.2f %s (%s)%n",
                evento.getTipo(), evento.getValor(), evento.getMoeda(), evento.getOperacao()
        );
    }
}