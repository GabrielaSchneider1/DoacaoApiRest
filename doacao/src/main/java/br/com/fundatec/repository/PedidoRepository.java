package br.com.fundatec.repository;

import br.com.fundatec.model.ItemPedido;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends CrudRepository<ItemPedido, Long> {
    @Query("from ItemPedido where idPedido=:idPedido")
	Iterable<ItemPedido> findAllByIdPedido(@Param(value = "idPedido") Long idPedido);
	
}
