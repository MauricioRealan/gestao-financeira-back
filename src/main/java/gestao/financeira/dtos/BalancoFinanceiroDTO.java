package gestao.financeira.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BalancoFinanceiroDTO {
	
	private List<Double> despesas;
	
	private Double totalDespesas;
	
	private List<Double> receitas;
	
	private Double totalReceitas;
	
	private Double saldo;
	
}
