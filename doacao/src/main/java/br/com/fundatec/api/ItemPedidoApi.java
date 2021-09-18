package br.com.fundatec.api;

import br.com.fundatec.model.ItemPedido;
import br.com.fundatec.model.PedidoDoacao;
import br.com.fundatec.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.validation.Valid;

@RestController()
public class ItemPedidoApi {

	private final PedidoService pedidoService;

	public ItemPedidoApi(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@PostMapping("/doacoes/{id}/itensPedido")
	public ResponseEntity<?> incluirPedido(@RequestBody @Valid ItemPedido itemPedido,
			@PathVariable(value = "id") Long idDoacao) throws URISyntaxException {
		itemPedido = pedidoService.incluir(itemPedido, idDoacao);
		return ResponseEntity.created(new URI("/doacoes/id/itensPedido")).build();
	}

	@GetMapping("/doacoes/{id}/itensPedido")
	public ResponseEntity<List<ItemPedido>> getItensPedido(@PathVariable(value = "id") Long idDoacao) {
		List<ItemPedido> itens = pedidoService.listarPorIdDoacao(idDoacao);
		if (itens.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(itens);
	}

	@DeleteMapping("/itensPedido/{id}")
	public ResponseEntity<?> excluirDoacao(@PathVariable Long id) {
		pedidoService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
    @PutMapping("/itensPedido/{id}")
    public ResponseEntity<?> atualizarItemPedido(@RequestBody @Valid ItemPedido itemPedido, @PathVariable Long id) {
		ItemPedido itemSalvo= pedidoService.atualizar(id, itemPedido);
		if (itemSalvo == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(itemSalvo);
    }

}