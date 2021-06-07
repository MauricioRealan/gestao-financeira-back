package gestao.financeira.repository;

import gestao.financeira.domain.Teste;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteRepository extends MongoRepository<Teste, String> {

}
