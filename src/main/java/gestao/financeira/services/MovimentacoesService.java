package gestao.financeira.services;

import gestao.financeira.domain.Movimentacoes;
import gestao.financeira.dtos.MovimentacoesDTO;
import gestao.financeira.repository.MovimentacoesRepository;
import gestao.financeira.repository.TesteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacoesService {
	
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
	
}
