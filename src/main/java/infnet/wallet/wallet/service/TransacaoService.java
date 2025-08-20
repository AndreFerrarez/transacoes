package infnet.wallet.wallet.service;

import infnet.wallet.wallet.model.Transacao;
import infnet.wallet.wallet.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    public Transacao salvar(Transacao t) {
        return repository.save(t);
    }

    public List<Transacao> listar() {
        return repository.findAll();
    }

    public BigDecimal calcularSaldo(String moeda) {
        return repository.findAll().stream()
                .filter(t -> t.getMoeda().equalsIgnoreCase(moeda))
                .map(t -> t.getTipo().equalsIgnoreCase("DEPOSITO") ? t.getValor() : t.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}