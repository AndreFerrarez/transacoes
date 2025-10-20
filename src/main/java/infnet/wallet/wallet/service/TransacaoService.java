package infnet.wallet.wallet.service;

import infnet.wallet.wallet.dto.HistoricoDTO;
import infnet.wallet.wallet.model.Transacao;
import infnet.wallet.wallet.historico.HistoricoPort;
import infnet.wallet.wallet.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;
    private final HistoricoPort historicoPort;

    public TransacaoService(TransacaoRepository repository,
                            HistoricoPort historicoPort) {
        this.repository = repository;
        this.historicoPort = historicoPort;
    }

    @Transactional
    public Transacao salvar(Transacao t) {
        boolean isCreate = (t.getId() == null);

        Transacao saved = repository.save(t);

        // registrar histórico via porta (sem acoplar a infra)
        String operacao = isCreate ? "CREATE" : "UPDATE";
        historicoPort.registrarHistorico(
                saved.getId(),
                saved.getTipo(),
                saved.getValor(),
                saved.getMoeda(),
                operacao
        );

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

    @Transactional
    public void excluir(Long id) {
        // snapshot antes de excluir
        Transacao t = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transacao não encontrada: " + id));

        // registrar histórico de DELETE via porta
        historicoPort.registrarHistorico(
                t.getId(),
                t.getTipo(),
                t.getValor(),
                t.getMoeda(),
                "DELETE"
        );

        repository.deleteById(id);
    }

    // consultas de histórico delegam à porta
    public List<HistoricoDTO> historicoPorTransacao(Long transacaoId) {
        return historicoPort.buscarPorTransacao(transacaoId);
    }

    public List<HistoricoDTO> historicoPorMoeda(String moeda) {
        return historicoPort.buscarPorMoeda(moeda);
    }
}
