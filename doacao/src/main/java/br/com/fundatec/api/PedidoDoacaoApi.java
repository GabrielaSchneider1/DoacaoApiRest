package br.com.fundatec.api;

import br.com.fundatec.model.PedidoDoacao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.fundatec.service.DoacaoService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PedidoDoacaoApi {

	private final DoacaoService doacaoService;

	public PedidoDoacaoApi(DoacaoService doacaoService) {
		this.doacaoService = doacaoService;
	}

	@GetMapping("/doacoes")
	public ResponseEntity<List<PedidoDoacao>> getDoacoes(
			@RequestParam(required = false, defaultValue = "") String nome) {
		List<PedidoDoacao> doacoes = doacaoService.listarDoacoes();
		if (doacoes.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(doacoes);
	}

	@GetMapping("/doacoes/{id}")
	public ResponseEntity<PedidoDoacao> getDoacao(@PathVariable Long id) {
		PedidoDoacao doacao = doacaoService.consultar(id);
		if (doacao != null) {
			return ResponseEntity.ok(doacao);
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/doacoes")
	@ApiOperation(value = "Faz a inclusao de um Pedido de Doacao no banco de dados", notes = "Valida se os campos obrigatorios foram preenchidos")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Pedido de Doacao incluido com sucesso!", response = PedidoDoacao.class) })
	public ResponseEntity<?> incluir(@Valid@RequestBody PedidoDoacao doacao) {
		
		try {
			doacao = doacaoService.incluir(doacao);
			return ResponseEntity.status(HttpStatus.CREATED).body(doacao);
		} catch (RuntimeException e) {
			ErroDto erroDto = new ErroDto();
			erroDto.setMensagem(e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(erroDto);
		}

	}

	@PutMapping("/doacoes/{id}")
	public ResponseEntity<?> atualizarDoacao(@PathVariable Long id, @Valid @RequestBody PedidoDoacao doacao) {
		doacao = doacaoService.atualizar(id, doacao);
		if (doacao == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(doacao);
	}

	@DeleteMapping("/doacoes/{id}")
	public ResponseEntity<?> excluirDoacao(@PathVariable Long id) {
		doacaoService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
