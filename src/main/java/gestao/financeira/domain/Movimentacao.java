package gestao.financeira.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Builder
@Getter
@ToString
@Document(collection = "movimentos")
public class Movimentacao {
	
	@Id
	private String id;
	
	private final String categoria;
	
	private final String data;
	
	private final Double valor;
	
	private final String movimento;

}
