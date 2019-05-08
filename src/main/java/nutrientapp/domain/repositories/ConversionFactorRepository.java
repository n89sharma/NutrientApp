package nutrientapp.domain.repositories;

import nutrientapp.domain.databaseobjects.ConversionFactor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversionFactorRepository extends MongoRepository<ConversionFactor, String> {
}
