package nutrientapp.domain.repositories;

import nutrientapp.user.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {
}