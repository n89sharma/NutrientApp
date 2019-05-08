package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.YieldAmountCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface YieldAmountRepository extends MongoRepository<YieldAmountCsv, String> {
    List<YieldAmountCsv> findByFoodId(int foodId);
}
