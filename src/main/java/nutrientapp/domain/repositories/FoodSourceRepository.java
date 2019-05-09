package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbFoodSource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodSourceRepository extends MongoRepository<DbFoodSource, String> {
}
