package fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversionFactorRepository extends MongoRepository<ConversionFactorFoodItem, Integer> {
}
