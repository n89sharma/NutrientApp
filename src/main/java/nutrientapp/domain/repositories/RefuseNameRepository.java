package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.RefuseName;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefuseNameRepository  extends MongoRepository<RefuseName, String> {
}
