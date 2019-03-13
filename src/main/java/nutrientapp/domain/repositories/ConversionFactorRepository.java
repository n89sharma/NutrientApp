package nutrientapp.domain.repositories;

import nutrientapp.domain.csvobjects.ConversionFactorCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversionFactorRepository extends MongoRepository<ConversionFactorCsv, String> {
    List<ConversionFactorCsv> findByFoodId(int foodId);
}
