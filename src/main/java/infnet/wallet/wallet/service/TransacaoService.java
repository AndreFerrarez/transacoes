package infnet.wallet.wallet.service;

import infnet.wallet.wallet.model.Transacao;
import infnet.wallet.wallet.model.TransacaoHistorico;
import infnet.wallet.wallet.repository.TransacaoHistoricoRepository;
import infnet.wallet.wallet.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class TransacaoService {

    private final TransacaoRepository repository;
    private final TransacaoHistoricoRepository historicoRepository;


    public TransacaoService(TransacaoRepository repository,
                            TransacaoHistoricoRepository historicoRepository) {
        this.repository = repository;
        this.historicoRepository = historicoRepository;
    }

    @Transactional
    public Transacao salvar(Transacao t) {
        boolean isCreate = (t.getId() == null);

        Transacao saved = repository.save(t);

        // registrar histórico
        String operacao = isCreate ? "CREATE" : "UPDATE";
        TransacaoHistorico h = new TransacaoHistorico(saved.getId(), saved.getTipo(), saved.getValor(), saved.getMoeda(), operacao);
        historicoRepository.save(h);

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
        // Consultar antes para obter snapshot
        Transacao t = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transacao não encontrada: " + id));

        // registrar histórico antes de excluir
        TransacaoHistorico h = new TransacaoHistorico(t.getId(), t.getTipo(), t.getValor(), t.getMoeda(), "DELETE");
        historicoRepository.save(h);

        repository.deleteById(id);
    }

    // endpoints utilitários para histórico
    public List<TransacaoHistorico> historicoPorTransacao(Long transacaoId) {
        return historicoRepository.findByTransacaoIdOrderByDataOperacaoDesc(transacaoId);
    }

    public List<TransacaoHistorico> historicoPorMoeda(String moeda) {
        return historicoRepository.findByMoedaIgnoreCaseOrderByDataOperacaoDesc(moeda);
    }
}