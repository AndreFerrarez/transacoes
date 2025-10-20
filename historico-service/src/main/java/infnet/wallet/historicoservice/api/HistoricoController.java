package infnet.wallet.historicoservice.api;

import infnet.wallet.historicoservice.model.TransacaoHistorico;
import infnet.wallet.historicoservice.repository.TransacaoHistoricoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/historicos")
public class HistoricoController {

    private final TransacaoHistoricoRepository repo;

    public HistoricoController(TransacaoHistoricoRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoHistorico registrar(@Valid @RequestBody TransacaoHistorico payload) {
        if (payload.getDataOperacao() == null) {
            payload.setDataOperacao(LocalDateTime.now());
        }
        return repo.save(payload);
    }

    @GetMapping("/transacao/{id}")
    public List<TransacaoHistorico> porTransacao(@PathVariable Long id) {
        return repo.findByTransacaoIdOrderByDataOperacaoDesc(id);
    }

    @GetMapping("/moeda/{moeda}")
    public List<TransacaoHistorico> porMoeda(@PathVariable String moeda) {
        return repo.findByMoedaIgnoreCaseOrderByDataOperacaoDesc(moeda);
    }
}