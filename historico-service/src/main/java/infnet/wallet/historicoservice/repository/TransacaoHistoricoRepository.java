package infnet.wallet.historicoservice.repository;

import infnet.wallet.historicoservice.model.TransacaoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoHistoricoRepository extends JpaRepository<TransacaoHistorico, Long> {

    List<TransacaoHistorico> findByTransacaoIdOrderByDataOperacaoDesc(Long transacaoId);

    List<TransacaoHistorico> findByMoedaIgnoreCaseOrderByDataOperacaoDesc(String moeda);
}
