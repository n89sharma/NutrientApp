package nutrientapp.domain.csvrepositories;

import nutrientapp.domain.csvobjects.RefuseAmountCsv;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RefuseAmountRepository extends MongoRepository<RefuseAmountCsv, String> {
    List<RefuseAmountCsv> findByFoodId(int foodId);
}
