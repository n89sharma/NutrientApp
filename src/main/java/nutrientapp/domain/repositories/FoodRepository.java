package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbFood;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<DbFood, String> {
}
