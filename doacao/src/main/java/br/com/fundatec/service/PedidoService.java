package br.com.fundatec.service;

import br.com.fundatec.model.PedidoDoacao;
import br.com.fundatec.model.ItemPedido;
import br.com.fundatec.repository.PedidoRepository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.google.common.collect.FluentIterable;

@Service
public class PedidoService {

        private final DoacaoService doacaoService;
        private final PedidoRepository pedidoRepository;

        public PedidoService (DoacaoService doacaoService, PedidoRepository pedidoRepository) {
            this.doacaoService = doacaoService;
            this.pedidoRepository = pedidoRepository;
        }

    public ItemPedido incluir(ItemPedido pedido, Long idDoacao) {
        PedidoDoacao doacao = doacaoService.consultar(idDoacao);
        pedido.setDoacao(doacao);
        return pedidoRepository.save(pedido);
    }

	public List<ItemPedido> listarPorIdDoacao(Long idPedido) {
		Iterable<ItemPedido> itensPedido = pedidoRepository.findAllByIdPedido(idPedido);
		return FluentIterable.from(itensPedido).toList();
	}
		

    public void excluir(Long id) {
    	pedidoRepository.deleteById(id);
    }

	public ItemPedido atualizar(Long id, @Valid ItemPedido itemPedido) {
		Optional<ItemPedido> itemSalvo = pedidoRepository.findById(id);
		ItemPedido itemASerSalvo = null;
		if(itemSalvo.isPresent()) {
			itemASerSalvo = itemSalvo.get();
			itemASerSalvo.setDescricao(itemPedido.getDescricao());
			itemASerSalvo.setIdPedido(itemPedido.getIdPedido());
			itemASerSalvo.setNome(itemPedido.getNome());
			itemASerSalvo.setQuantidade(itemPedido.getQuantidade());
			itemASerSalvo.setUnidade(itemPedido.getUnidade());
			
			pedidoRepository.save(itemASerSalvo);
		}
		return itemASerSalvo;
	}
    
    
    
    }


