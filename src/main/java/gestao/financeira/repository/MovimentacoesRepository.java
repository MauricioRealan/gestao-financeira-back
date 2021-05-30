package gestao.financeira.repository;

import gestao.financeira.domain.Movimentacoes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface MovimentacoesRepository extends MongoRepository<Movimentacoes, String> {

}
