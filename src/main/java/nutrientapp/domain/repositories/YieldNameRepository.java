package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbYieldName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YieldNameRepository extends MongoRepository<DbYieldName, String> {
}
