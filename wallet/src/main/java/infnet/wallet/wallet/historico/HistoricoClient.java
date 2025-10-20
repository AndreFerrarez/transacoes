package infnet.wallet.wallet.historico;

import infnet.wallet.wallet.dto.HistoricoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "historico-service", url = "${historico.service.url}")
public interface HistoricoClient {

    @PostMapping("/historicos")
    HistoricoDTO registrar(@RequestBody HistoricoDTO payload);

    @GetMapping("/historicos/transacao/{id}")
    List<HistoricoDTO> porTransacao(@PathVariable("id") Long id);

    @GetMapping("/historicos/moeda/{moeda}")
    List<HistoricoDTO> porMoeda(@PathVariable("moeda") String moeda);
}
