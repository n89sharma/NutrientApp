package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.MeasureNameCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureNameRepository extends MongoRepository<MeasureNameCsv, String> {
    MeasureNameCsv findByMeasureId(int measureId);
}
