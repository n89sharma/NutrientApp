package nutrientapp.fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodItemRepository extends MongoRepository<FoodItem, String> {
    public FoodItem findByFoodId(int foodId);
}
