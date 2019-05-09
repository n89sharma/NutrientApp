package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbNutrientName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutrientNameRepository extends MongoRepository<DbNutrientName, String> {
}
