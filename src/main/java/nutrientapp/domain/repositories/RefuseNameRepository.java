package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbRefuseName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefuseNameRepository extends MongoRepository<DbRefuseName, String> {
}
