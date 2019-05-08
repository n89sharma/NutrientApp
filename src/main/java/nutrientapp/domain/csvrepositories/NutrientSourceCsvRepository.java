package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.NutrientSourceCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutrientSourceCsvRepository extends MongoRepository<NutrientSourceCsv, String> {

}
