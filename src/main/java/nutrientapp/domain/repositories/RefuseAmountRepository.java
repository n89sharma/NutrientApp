package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.DbRefuseAmount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefuseAmountRepository extends MongoRepository<DbRefuseAmount, String> {
}
