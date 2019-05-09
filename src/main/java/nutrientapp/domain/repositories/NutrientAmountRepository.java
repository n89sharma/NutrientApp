package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbNutrientAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NutrientAmountRepository extends MongoRepository<DbNutrientAmount, String> {
    List<DbNutrientAmount> findByFoodId(String foodId);
}
