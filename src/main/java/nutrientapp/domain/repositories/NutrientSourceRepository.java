package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.NutrientSource;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutrientSourceRepository  extends MongoRepository<NutrientSource, String> {
}
