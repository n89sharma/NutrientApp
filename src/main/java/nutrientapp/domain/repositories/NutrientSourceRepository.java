package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbNutrientSource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutrientSourceRepository extends MongoRepository<DbNutrientSource, String> {
}
