package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.NutrientAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutrientAmountRepository  extends MongoRepository<NutrientAmount, String> {
}
