package infnet.wallet.wallet.historico;

import infnet.wallet.wallet.dto.HistoricoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HistoricoRestAdapter implements HistoricoPort {

    private final HistoricoClient client;

    public HistoricoRestAdapter(HistoricoClient client) {
        this.client = client;
    }

    @Override
    public void registrarHistorico(Long transacaoId,
                                   String tipo,
                                   BigDecimal valor,
                                   String moeda,
                                   String operacao) {
        HistoricoDTO payload = new HistoricoDTO();
        payload.setTransacaoId(transacaoId);
        payload.setTipo(tipo);
        payload.setValor(valor);
        payload.setMoeda(moeda);
        payload.setOperacao(operacao);

        client.registrar(payload);
    }

    @Override
    public List<HistoricoDTO> buscarPorTransacao(Long transacaoId) {
        return client.porTransacao(transacaoId);
    }

    @Override
    public List<HistoricoDTO> buscarPorMoeda(String moeda) {
        return client.porMoeda(moeda);
    }
}