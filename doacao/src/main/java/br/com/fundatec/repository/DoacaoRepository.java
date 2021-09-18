package br.com.fundatec.repository;

import br.com.fundatec.model.PedidoDoacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DoacaoRepository extends CrudRepository<PedidoDoacao, Long> {

        Optional<PedidoDoacao> findById(Long id);


}
