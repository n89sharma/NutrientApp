package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.NutrientAmountCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NutrientAmountCsvRepository extends MongoRepository<NutrientAmountCsv, String> {
    List<NutrientAmountCsv> findByFoodId(int foodId);
}
