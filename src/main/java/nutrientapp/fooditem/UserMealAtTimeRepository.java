package nutrientapp.fooditem;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMealAtTimeRepository extends MongoRepository<UserMealAtTime, String> {
    public List<UserMealAtTime> findByUserId(String userId);
}
