package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbYieldAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YieldAmountRepository extends MongoRepository<DbYieldAmount, String> {
}
