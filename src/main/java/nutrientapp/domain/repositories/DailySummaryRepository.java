package nutrientapp.domain.repositories;

import nutrientapp.domain.internal.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {
    DailySummary findByUserIdAndDate(String userId, Date date);
}