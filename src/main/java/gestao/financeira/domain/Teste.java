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
@Document(collection = "teste")
public class Teste {
	
	@Id
	private String _id;
	
	private final String teste;
}
