package nutrientapp.domain.csvrepositories;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import nutrientapp.domain.internal.DailySummary;

public interface DailySummaryRepository extends MongoRepository<DailySummary, String> {
    DailySummary findByUserIdAndDate(String userId, Date date);
}