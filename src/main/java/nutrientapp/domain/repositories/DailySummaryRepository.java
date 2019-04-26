package nutrientapp.domain.repositories;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import nutrientapp.user.DailySummary;

public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {
    DailySummary findByUserIdAndDate(String userId, Date date);
}