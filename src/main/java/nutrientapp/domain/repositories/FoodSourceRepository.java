package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.FoodSource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodSourceRepository extends MongoRepository<FoodSource, String> {
}
