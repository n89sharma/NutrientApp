package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.YieldAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YieldAmountRepository extends MongoRepository<YieldAmount, String> {
}
