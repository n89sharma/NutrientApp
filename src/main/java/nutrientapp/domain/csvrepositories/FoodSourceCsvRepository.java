package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.FoodSourceCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodSourceCsvRepository extends MongoRepository<FoodSourceCsv, String> {
}
