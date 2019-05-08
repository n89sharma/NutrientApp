package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.FoodGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodGroupRepository extends MongoRepository<FoodGroup, String> {
}
