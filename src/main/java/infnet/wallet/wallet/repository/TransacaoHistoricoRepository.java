package infnet.wallet.wallet.repository;

import infnet.wallet.wallet.model.TransacaoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoHistoricoRepository extends JpaRepository<TransacaoHistorico, Long> {

    List<TransacaoHistorico> findByTransacaoIdOrderByDataOperacaoDesc(Long transacaoId);

    List<TransacaoHistorico> findByMoedaIgnoreCaseOrderByDataOperacaoDesc(String moeda);
}
