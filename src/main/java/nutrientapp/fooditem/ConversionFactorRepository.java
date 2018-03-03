package nutrientapp.fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversionFactorRepository extends MongoRepository<Measures, String> {
    public Measures findByFoodId(int foodId);
}
