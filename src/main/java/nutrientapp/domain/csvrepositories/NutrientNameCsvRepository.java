package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.NutrientNameCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NutrientNameCsvRepository extends MongoRepository<NutrientNameCsv, String> {
    NutrientNameCsv findByNutrientNameId(int nutrientNameId);
}
