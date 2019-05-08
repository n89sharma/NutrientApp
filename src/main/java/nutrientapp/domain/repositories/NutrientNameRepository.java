package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.NutrientName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutrientNameRepository extends MongoRepository<NutrientName, String> {
}
