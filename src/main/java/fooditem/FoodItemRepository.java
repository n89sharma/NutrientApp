package fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FoodItemRepository extends MongoRepository<FoodItem, Integer> {
    public List<FoodItem> findByFoodId(Integer foodId);
}
