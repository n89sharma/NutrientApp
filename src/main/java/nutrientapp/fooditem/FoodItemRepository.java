package nutrientapp.fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodItemRepository extends MongoRepository<FoodItem, String> {
    public FoodItem findByFoodId(int foodId);
}
