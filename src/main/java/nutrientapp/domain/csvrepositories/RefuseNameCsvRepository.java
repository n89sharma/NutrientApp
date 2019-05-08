package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.RefuseNameCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefuseNameCsvRepository extends MongoRepository<RefuseNameCsv, String> {
    RefuseNameCsv findByRefuseId(int refuseId);
}
