package infnet.wallet.wallet.historico;

import infnet.wallet.wallet.dto.HistoricoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface HistoricoPort {

    void registrarHistorico(Long transacaoId,
                            String tipo,
                            BigDecimal valor,
                            String moeda,
                            String operacao);

    List<HistoricoDTO> buscarPorTransacao(Long transacaoId);

    List<HistoricoDTO> buscarPorMoeda(String moeda);
}
