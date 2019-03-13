package nutrientapp.domain.repositories;

import nutrientapp.user.UserWeightAtTime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserWeightRepository extends MongoRepository<UserWeightAtTime, String> {
    List<UserWeightAtTime> findByUserId(String userId);
}
