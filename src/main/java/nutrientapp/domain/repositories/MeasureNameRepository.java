package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.MeasureName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureNameRepository extends MongoRepository<MeasureName, String> {
}
