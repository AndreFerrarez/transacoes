package infnet.wallet.wallet.repository;


import infnet.wallet.wallet.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {


    List<Transacao> findByMoedaIgnoreCase(String moeda);

    List<Transacao> findByTipoIgnoreCase(String tipo);

    // Pra futuras necessidades: paging/sorting já é suportado por JpaRepository
}
