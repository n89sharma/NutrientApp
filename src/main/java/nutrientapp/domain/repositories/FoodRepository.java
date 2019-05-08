package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<Food, String> {
}
