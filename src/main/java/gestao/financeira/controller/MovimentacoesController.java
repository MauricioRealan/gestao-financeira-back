package gestao.financeira.controller;

import gestao.financeira.dtos.BalancoFinanceiroDTO;
import gestao.financeira.dtos.MovimentacaoDTO;
import gestao.financeira.services.MovimentacoesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class MovimentacoesController {
	
	private final MovimentacoesService movimentacoesService;
	
	public MovimentacoesController(final MovimentacoesService movimentacoesService) {
		this.movimentacoesService = movimentacoesService;
	}
	
	@GetMapping(path = "/buscar")
	public ResponseEntity<List<MovimentacaoDTO>> findAll() {
		return ResponseEntity.ok(this.movimentacoesService.findAll());
	}
	
	@PostMapping(path = "/salvar")
	public ResponseEntity<String> save(@RequestBody @Valid final MovimentacaoDTO movimentacaoDTO) {
		return ResponseEntity.ok(this.movimentacoesService.save(movimentacaoDTO));
	}
	
	@PostMapping(path = "/excluir/{idMovimentacao}")
	public ResponseEntity<List<MovimentacaoDTO>> delete(@PathVariable("idMovimentacao") @NotNull final String idMovimentacao) {
		return ResponseEntity.ok(this.movimentacoesService.delete(idMovimentacao));
	}
	
	@GetMapping(path = "/balanco-financeiro")
	public ResponseEntity<BalancoFinanceiroDTO> findBalancoFinanceiro() {
		return ResponseEntity.ok(this.movimentacoesService.buildBalancoFinanceiro());
	}
	
	
}
