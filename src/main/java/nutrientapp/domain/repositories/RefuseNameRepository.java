package nutrientapp.domain.repositories;

import nutrientapp.domain.csvobjects.RefuseNameCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefuseNameRepository extends MongoRepository<RefuseNameCsv, String> {
    RefuseNameCsv findByRefuseId(int refuseId);
}
