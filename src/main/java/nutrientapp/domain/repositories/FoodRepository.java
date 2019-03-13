package nutrientapp.domain.repositories;

import nutrientapp.domain.csvobjects.FoodCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<FoodCsv, String> {
    FoodCsv findByFoodId(int foodId);
}
