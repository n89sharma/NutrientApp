package nutrientapp.domain.repositories;

import nutrientapp.domain.csvobjects.NutrientAmountCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NutrientAmountRepository extends MongoRepository<NutrientAmountCsv, String> {
    List<NutrientAmountCsv> findByFoodId(int foodId);
}
