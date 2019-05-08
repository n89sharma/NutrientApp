package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.ConversionFactor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversionFactorRepository extends MongoRepository<ConversionFactor, String> {
    ConversionFactor findByFoodIdAndMeasureId(String foodId, String measureId);
    List<ConversionFactor> findByFoodId(String foodId);
}
