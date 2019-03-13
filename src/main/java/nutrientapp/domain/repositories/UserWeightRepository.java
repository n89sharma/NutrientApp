package nutrientapp.domain.repositories;

import nutrientapp.user.BodyWeight;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserWeightRepository extends MongoRepository<BodyWeight, String> {
    List<BodyWeight> findByUserId(String userId);
}
