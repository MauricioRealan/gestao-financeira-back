package gestao.financeira.services;

import gestao.financeira.domain.Movimentacoes;
import gestao.financeira.dtos.BalancoFinanceiroDTO;
import gestao.financeira.dtos.MovimentacoesDTO;
import gestao.financeira.repository.MovimentacoesRepository;
import gestao.financeira.repository.TesteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacoesService {
	
	public static final String DESPESA = "Despesa";
	public static final String RECEITA = "Receita";
	private final MovimentacoesRepository movimentacoesRepository;
	
	private final TesteRepository testeRepository;
	
	public MovimentacoesService(final MovimentacoesRepository movimentacoesRepository,
								final TesteRepository testeRepository) {
		this.movimentacoesRepository = movimentacoesRepository;
		this.testeRepository = testeRepository;
	}
	
	public List<MovimentacoesDTO> findAll() {
		return this.movimentacoesRepository.findAll().stream()
				.map(movimentacoes -> toDTO(movimentacoes))
				.collect(Collectors.toList());
	}
	
	public String save(final MovimentacoesDTO movimentacoesDTO) {
		final Movimentacoes save = this.movimentacoesRepository.save(toEntity(movimentacoesDTO));
		return save.getId();
	}
	
	public List<MovimentacoesDTO> delete(final String idMovimentacao) {
		this.movimentacoesRepository.deleteById(idMovimentacao);
		return this.movimentacoesRepository.findAll().stream()
				.map(movimentacoes -> toDTO(movimentacoes))
				.collect(Collectors.toList());
	}
	
	public BalancoFinanceiroDTO buildBalancoFinanceiro() {
		List<MovimentacoesDTO> movimentacoes = this.findAll();
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
	
	public static MovimentacoesDTO toDTO(final Movimentacoes movimentacoes) {
		return MovimentacoesDTO.builder()
				.id(movimentacoes.getId())
				.categoria(movimentacoes.getCategoria())
				.data(movimentacoes.getData())
				.valor(movimentacoes.getValor())
				.movimento(movimentacoes.getMovimento())
				.build();
	}
	
	public static Movimentacoes toEntity(final MovimentacoesDTO movimentacoesDTO){
		return Movimentacoes.builder()
				.id(movimentacoesDTO.getId())
				.categoria(movimentacoesDTO.getCategoria())
				.data(movimentacoesDTO.getData())
				.valor(movimentacoesDTO.getValor())
				.movimento(movimentacoesDTO.getMovimento())
				.build();
	}
	
	private List<Double> mapMovimentacoesByCategoria(List<MovimentacoesDTO> movimentacoes, String categoria) {
		return movimentacoes.stream()
				.filter(movimentacao -> movimentacao.getCategoria().equals(categoria))
				.map(MovimentacoesDTO::getValor)
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
