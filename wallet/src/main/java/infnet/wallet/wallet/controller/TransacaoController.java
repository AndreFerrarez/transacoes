package infnet.wallet.wallet.controller;



import infnet.wallet.wallet.model.Transacao;
import infnet.wallet.wallet.service.TransacaoService;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public Transacao criar(@RequestBody Transacao transacao) {
        return service.salvar(transacao);
    }

    @GetMapping
    public List<Transacao> listar() {
        return service.listar();
    }

    @GetMapping("/saldo/{moeda}")
    public BigDecimal saldo(@PathVariable String moeda) {
        return service.calcularSaldo(moeda);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

}
