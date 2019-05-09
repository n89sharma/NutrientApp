package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbFoodGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodGroupRepository extends MongoRepository<DbFoodGroup, String> {
}
