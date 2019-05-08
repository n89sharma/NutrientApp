package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.RefuseAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefuseAmountRepository  extends MongoRepository<RefuseAmount, String> {
}
