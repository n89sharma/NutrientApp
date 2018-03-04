package nutrientapp.fooditem;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserWeightRepository extends MongoRepository<UserWeightAtTime, String> {
    public List<UserWeightAtTime> findByUserId(String userId);
}
