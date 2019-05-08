package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.MeasureNameCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureNameCsvRepository extends MongoRepository<MeasureNameCsv, String> {
    MeasureNameCsv findByMeasureId(int measureId);
}
