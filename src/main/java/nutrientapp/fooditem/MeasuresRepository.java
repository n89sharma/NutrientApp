package nutrientapp.fooditem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasuresRepository extends MongoRepository<Measures, String> {
    public Measures findByFoodId(int foodId);
}
