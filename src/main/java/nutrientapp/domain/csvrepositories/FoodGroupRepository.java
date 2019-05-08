package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.FoodGroupCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodGroupRepository extends MongoRepository<FoodGroupCsv, String> {
    FoodGroupCsv findByFoodGroupId(int foodGroupId);
}
