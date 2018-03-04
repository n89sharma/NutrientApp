package nutrientapp.fooditem;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserWeightRepository userWeightRepository;
    private UserMealAtTimeRepository userMealAtTimeRepository;

    @Autowired
    public UserService(UserWeightRepository userWeightRepository, UserMealAtTimeRepository userMealAtTimeRepository) {
        this.userWeightRepository = userWeightRepository;
        this.userMealAtTimeRepository = userMealAtTimeRepository;
    }

    public List<UserWeightAtTime> saveWeightAtTime(UserWeightAtTime weightAtTime) {
        userWeightRepository.save(weightAtTime);
        return userWeightRepository.findByUserId(weightAtTime.getUserId());
    }

    public List<UserMealAtTime> saveMealAtTime(UserMealAtTime mealAtTime) {
        userMealAtTimeRepository.save(mealAtTime);
        return userMealAtTimeRepository.findByUserId(mealAtTime.getUserId());
    }
}