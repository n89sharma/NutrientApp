package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbConversionFactor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversionFactorRepository extends MongoRepository<DbConversionFactor, String> {
    DbConversionFactor findByFoodIdAndMeasureId(String foodId, String measureId);

    List<DbConversionFactor> findByFoodId(String foodId);
}
