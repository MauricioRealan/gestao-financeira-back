package gestao.financeira;

import gestao.financeira.domain.Movimentacao;
import gestao.financeira.dtos.BalancoFinanceiroDTO;
import gestao.financeira.dtos.MovimentacaoDTO;
import gestao.financeira.repository.MovimentacoesRepository;
import gestao.financeira.services.MovimentacoesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovimentacaoServiceTest {
	
	@Mock
	private MovimentacoesRepository repository;
	
	private MovimentacoesService service;
	
	@BeforeEach
	public void setup() {
		this.service = new MovimentacoesService(this.repository);
	}
	
	@Test
	@DisplayName("deve buscar todas as movimentacoes realizadas com sucesso")
	public void testFindAll()  {
		List<Movimentacao> movimentacoesMock = new ArrayList<>();
		Movimentacao movimentacaoMock = Movimentacao.builder()
				.id("2ijfid9434")
				.movimento("movimento de teste")
				.categoria("Despesa")
				.valor(75.5)
				.data("07/06/2021")
				.build();
		movimentacoesMock.add(movimentacaoMock);
		
		when(repository.findAll()).thenReturn(movimentacoesMock);
		final List<MovimentacaoDTO> response = this.service.findAll();
		
		assertEquals(response.size(), 1L);
		assertEquals(response.get(0).getId(), movimentacaoMock.getId());
	}
	
	@Test
	@DisplayName("deve gerar o balanco financeiro das movimentacoes realizadas")
	public void testBuildBalancoFinanceiro()  {
		List<Movimentacao> movimentacoesMock = new ArrayList<>();
		Movimentacao despesa1Mock = Movimentacao.builder()
				.id("2ijfid9434")
				.movimento("despesa de teste 1")
				.categoria("Despesa")
				.valor(75.5)
				.data("07/06/2021")
				.build();
		Movimentacao despesa2Mock = Movimentacao.builder()
				.id("2ijfid9839")
				.movimento("despesa de teste 2")
				.categoria("Despesa")
				.valor(50.8)
				.data("07/06/2021")
				.build();
		Movimentacao receita1Mock = Movimentacao.builder()
				.id("9yjfid7434")
				.movimento("receita de teste 1")
				.categoria("Receita")
				.valor(710.0)
				.data("07/06/2021")
				.build();
		Movimentacao receita2Mock = Movimentacao.builder()
				.id("2ijfid9839")
				.movimento("receita de teste 2")
				.categoria("Receita")
				.valor(15.5)
				.data("07/06/2021")
				.build();
		
		movimentacoesMock.add(despesa1Mock);
		movimentacoesMock.add(despesa2Mock);
		movimentacoesMock.add(receita1Mock);
		movimentacoesMock.add(receita2Mock);
		
		when(repository.findAll()).thenReturn(movimentacoesMock);
		final BalancoFinanceiroDTO response = this.service.buildBalancoFinanceiro();
		
		assertEquals(response.getTotalDespesas(), 126.3);
		assertEquals(response.getTotalReceitas(), 725.5);
		assertEquals(response.getSaldo(), 599.2);
		
	}

}
