package infnet.wallet.wallet.repository;


import infnet.wallet.wallet.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
