package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbMeasureName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureNameRepository extends MongoRepository<DbMeasureName, String> {
}
