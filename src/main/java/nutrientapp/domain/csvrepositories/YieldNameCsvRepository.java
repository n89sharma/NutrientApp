package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.YieldNameCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YieldNameCsvRepository extends MongoRepository<YieldNameCsv, String> {
    YieldNameCsv findByYieldId(int yieldId);
}
