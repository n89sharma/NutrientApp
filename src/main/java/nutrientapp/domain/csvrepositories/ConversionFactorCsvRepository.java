package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.ConversionFactorCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversionFactorCsvRepository extends MongoRepository<ConversionFactorCsv, String> {
    List<ConversionFactorCsv> findByFoodId(int foodId);

    ConversionFactorCsv findByFoodIdAndMeasureId(int foodId, int measureId);
}
