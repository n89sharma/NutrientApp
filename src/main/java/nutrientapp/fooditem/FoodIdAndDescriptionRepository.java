package nutrientapp.fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodIdAndDescriptionRepository extends MongoRepository<FoodIdAndDescription, String> {

}
