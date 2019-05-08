package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.FoodCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodCsvRepository extends MongoRepository<FoodCsv, String> {
    FoodCsv findByFoodId(int foodId);
}
