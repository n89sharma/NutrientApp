package nutrientapp.fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodGroupRepository extends MongoRepository<FoodGroup, String> {

}
