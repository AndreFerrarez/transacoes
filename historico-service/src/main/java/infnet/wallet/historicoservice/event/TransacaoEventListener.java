package infnet.wallet.historicoservice.event;

import infnet.wallet.historicoservice.model.TransacaoHistorico;
import infnet.wallet.historicoservice.repository.TransacaoHistoricoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
public class TransacaoEventListener {

    private final TransacaoHistoricoRepository repository;

    public TransacaoEventListener(TransacaoHistoricoRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${rabbitmq.queue.transacoes}")
    public void consumirEvento(Map<String, Object> evento) {
        try {
            log.info("[RabbitMQ] Evento recebido: {}", evento);

            // 1. Extração dos dados
            Long idDaWallet = ((Number) evento.get("transacaoId")).longValue();
            String tipo = (String) evento.get("tipo");
            BigDecimal valor = new BigDecimal(evento.get("valor").toString());
            String moeda = (String) evento.get("moeda");
            String operacao = (String) evento.get("operacao");

            // 2. Criação do Objeto MongoDB
            TransacaoHistorico historico = new TransacaoHistorico();

            // O MongoDB gera o ID String automaticamente.
            historico.setTransacaoIdOriginal(idDaWallet);

            historico.setTipo(tipo);
            historico.setValor(valor);
            historico.setMoeda(moeda);
            historico.setOperacao(operacao);
            historico.setDataOperacao(LocalDateTime.now());

            // 3. Salva no MongoDB
            repository.save(historico);
            log.info("[RabbitMQ] Histórico salvo no Mongo: ID Wallet={}, Operação={}", idDaWallet, operacao);

        } catch (Exception e) {
            log.error("Erro ao processar evento RabbitMQ: {}", e.getMessage(), e);
        }
    }
}