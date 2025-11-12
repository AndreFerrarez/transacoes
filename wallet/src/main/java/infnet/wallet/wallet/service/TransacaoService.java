package infnet.wallet.wallet.service;

import infnet.wallet.wallet.dto.HistoricoDTO;
import infnet.wallet.wallet.event.TransacaoCriadaEvent;
import infnet.wallet.wallet.event.TransacaoEventPublisher;
import infnet.wallet.wallet.historico.HistoricoPort;
import infnet.wallet.wallet.model.Transacao;

import infnet.wallet.wallet.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;
//    private final HistoricoPort historicoPort;
    private final TransacaoEventPublisher eventPublisher;

    public TransacaoService(TransacaoRepository repository,
                            TransacaoEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Transacao salvar(Transacao t) {
        boolean isCreate = (t.getId() == null);

        Transacao saved = repository.save(t);
        String operacao = isCreate ? "CREATE" : "UPDATE";

//        // etapa 3 - registrar histórico via porta (sem acoplar a infra) etapa 3
//        String operacao = isCreate ? "CREATE" : "UPDATE";
//        historicoPort.registrarHistorico(
//                saved.getId(),
//                saved.getTipo(),
//                saved.getValor(),
//                saved.getMoeda(),
//                operacao
//        );
        // etapa 4 - Publica o evento no RabbitMQ (ao invés de chamar historicoPort)
        TransacaoCriadaEvent evento = new TransacaoCriadaEvent(
                saved.getId(),
                saved.getTipo(),
                saved.getValor(),
                saved.getMoeda(),
                saved.getData(),
                operacao
        );

        eventPublisher.publicarEvento(evento);

        return saved;
    }

    public List<Transacao> listar() {
        return repository.findAll();
    }

    public BigDecimal calcularSaldo(String moeda) {
        return repository.findByMoedaIgnoreCase(moeda).stream()
                .map(t -> "DEPOSITO".equalsIgnoreCase(t.getTipo()) ? t.getValor() : t.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // etapa 3
//    @Transactional
//    public void excluir(Long id) {
//        // snapshot antes de excluir
//        Transacao t = repository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Transacao não encontrada: " + id));
//
//        // registrar histórico de DELETE via porta
//        historicoPort.registrarHistorico(
//                t.getId(),
//                t.getTipo(),
//                t.getValor(),
//                t.getMoeda(),
//                "DELETE"
//        );
//
//        repository.deleteById(id);
//    }

    // etapa 4
    @Transactional
    public void excluir(Long id) {
        Transacao t = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transacao não encontrada: " + id));

        // 🔁 Publica evento de exclusão no RabbitMQ
        TransacaoCriadaEvent evento = new TransacaoCriadaEvent(
                t.getId(),
                t.getTipo(),
                t.getValor(),
                t.getMoeda(),
                t.getData(),
                "DELETE"
        );

        eventPublisher.publicarEvento(evento);

        repository.deleteById(id);
    }

//    // consultas de histórico delegam à porta
//    public List<HistoricoDTO> historicoPorTransacao(Long transacaoId) {
//        return historicoPort.buscarPorTransacao(transacaoId);
//    }
//
//    public List<HistoricoDTO> historicoPorMoeda(String moeda) {
//        return historicoPort.buscarPorMoeda(moeda);
//    }
}
