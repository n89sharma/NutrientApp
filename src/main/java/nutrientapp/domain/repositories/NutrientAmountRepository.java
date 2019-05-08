package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.NutrientAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NutrientAmountRepository extends MongoRepository<NutrientAmount, String> {
    List<NutrientAmount> findByFoodId(String foodId);
}
