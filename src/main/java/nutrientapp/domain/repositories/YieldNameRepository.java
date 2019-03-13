package nutrientapp.domain.repositories;

import nutrientapp.domain.csvobjects.YieldNameCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YieldNameRepository extends MongoRepository<YieldNameCsv, String> {
    YieldNameCsv findByYieldId(int yieldId);
}
