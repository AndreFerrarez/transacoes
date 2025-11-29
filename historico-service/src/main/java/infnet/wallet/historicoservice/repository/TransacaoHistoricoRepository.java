package infnet.wallet.historicoservice.repository;

import infnet.wallet.historicoservice.model.TransacaoHistorico;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TransacaoHistoricoRepository extends MongoRepository<TransacaoHistorico, String> {

    // Ajustado para buscar pelo campo novo "transacaoIdOriginal"
    List<TransacaoHistorico> findByTransacaoIdOriginalOrderByDataOperacaoDesc(Long transacaoIdOriginal);

    // Declarado explicitamente para Controller poder usar
    List<TransacaoHistorico> findByMoedaIgnoreCaseOrderByDataOperacaoDesc(String moeda);
}