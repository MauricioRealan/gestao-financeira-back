package gestao.financeira.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MovimentacoesDTO {
	
	private String id;
	
	private String categoria;
	
	private String data;
	
	private Double valor;
	
	private String movimento;
}
