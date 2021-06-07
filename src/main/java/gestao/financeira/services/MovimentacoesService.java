package gestao.financeira.services;

import gestao.financeira.domain.Movimentacao;
import gestao.financeira.dtos.BalancoFinanceiroDTO;
import gestao.financeira.dtos.MovimentacaoDTO;
import gestao.financeira.repository.MovimentacoesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacoesService {
	
	public static final String DESPESA = "Despesa";
	public static final String RECEITA = "Receita";
	private final MovimentacoesRepository movimentacoesRepository;
	
	
	public MovimentacoesService(final MovimentacoesRepository movimentacoesRepository) {
		this.movimentacoesRepository = movimentacoesRepository;
	}
	
	public List<MovimentacaoDTO> findAll() {
		return this.movimentacoesRepository.findAll().stream()
				.map(movimentacao -> toDTO(movimentacao))
				.collect(Collectors.toList());
	}
	
	public String save(final MovimentacaoDTO movimentacaoDTO) {
		final Movimentacao save = this.movimentacoesRepository.save(toEntity(movimentacaoDTO));
		return save.getId();
	}
	
	public List<MovimentacaoDTO> delete(final String idMovimentacao) {
		this.movimentacoesRepository.deleteById(idMovimentacao);
		return this.movimentacoesRepository.findAll().stream()
				.map(movimentacao -> toDTO(movimentacao))
				.collect(Collectors.toList());
	}
	
	public BalancoFinanceiroDTO buildBalancoFinanceiro() {
		List<MovimentacaoDTO> movimentacoes = this.findAll();
		final List<Double> despesas = mapMovimentacoesByCategoria(movimentacoes, DESPESA);
		final List<Double> receitas = mapMovimentacoesByCategoria(movimentacoes, RECEITA);
		final double totalDespesas = despesas.stream()
				.mapToDouble(Double::doubleValue)
				.sum();
		final double totalReceitas = receitas.stream()
				.mapToDouble(Double::doubleValue)
				.sum();
		return buildBalancoFinanceiroDTO(despesas, receitas, totalDespesas, totalReceitas);
	}
	
	public static MovimentacaoDTO toDTO(final Movimentacao movimentacao) {
		return MovimentacaoDTO.builder()
				.id(movimentacao.getId())
				.categoria(movimentacao.getCategoria())
				.data(movimentacao.getData())
				.valor(movimentacao.getValor())
				.movimento(movimentacao.getMovimento())
				.build();
	}
	
	public static Movimentacao toEntity(final MovimentacaoDTO movimentacaoDTO){
		return Movimentacao.builder()
				.id(movimentacaoDTO.getId())
				.categoria(movimentacaoDTO.getCategoria())
				.data(movimentacaoDTO.getData())
				.valor(movimentacaoDTO.getValor())
				.movimento(movimentacaoDTO.getMovimento())
				.build();
	}
	
	private List<Double> mapMovimentacoesByCategoria(List<MovimentacaoDTO> movimentacoes, String categoria) {
		return movimentacoes.stream()
				.filter(movimentacao -> movimentacao.getCategoria().equals(categoria))
				.map(MovimentacaoDTO::getValor)
				.collect(Collectors.toList());
	}
	
	private BalancoFinanceiroDTO buildBalancoFinanceiroDTO(List<Double> despesas, List<Double> receitas, double totalDespesas, double totalReceitas) {
		return BalancoFinanceiroDTO.builder()
				.despesas(despesas)
				.totalDespesas(totalDespesas)
				.receitas(receitas)
				.totalReceitas(totalReceitas)
				.saldo(totalReceitas - totalDespesas)
				.build();
	}
	
}
