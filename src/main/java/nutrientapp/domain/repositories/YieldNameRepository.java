package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.YieldName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YieldNameRepository extends MongoRepository<YieldName, String> {
}
