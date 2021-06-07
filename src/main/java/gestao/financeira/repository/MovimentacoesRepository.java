package gestao.financeira.repository;

import gestao.financeira.domain.Movimentacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacoesRepository extends MongoRepository<Movimentacao, String> {

}
