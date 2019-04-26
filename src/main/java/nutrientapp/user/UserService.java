package nutrientapp.user;

import lombok.val;
import nutrientapp.domain.repositories.ConversionFactorRepository;
import nutrientapp.domain.repositories.DailySummaryRepository;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.UserWeightRepository;
import nutrientapp.user.DailySummary.PortionIds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserWeightRepository userWeightRepository;
    private ConversionFactorRepository conversionFactorRepository;
    private FoodRepository foodRepository;
    private DailySummaryRepository dailySummaryRepository;

    @Autowired
    public UserService(
      UserWeightRepository userWeightRepository,
      ConversionFactorRepository conversionFactorRepository,
      FoodRepository foodRepository,
      DailySummaryRepository dailySummaryRepository) {

        this.userWeightRepository = userWeightRepository;
        this.conversionFactorRepository = conversionFactorRepository;
        this.foodRepository = foodRepository;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    public List<BodyWeight> saveWeightAtTime(BodyWeight weightAtTime) {
        userWeightRepository.save(weightAtTime);
        return userWeightRepository.findByUserId(weightAtTime.getUserId());
    }

    public String saveDailySummary(DailySummary dailySummary) {
        if(isDailySummaryValid(dailySummary)) {
            return dailySummaryRepository.save(dailySummary).getId();
        }
        else {
            return "Error: Either the food IDs or the measure IDs are not valid.";
        }
    }

    private boolean isDailySummaryValid(DailySummary dailySummary) {
        return dailySummary.getBreakfast().stream().allMatch(this::isFoodMeasureAndServingValid) &&
                dailySummary.getLunch().stream().allMatch(this::isFoodMeasureAndServingValid) &&
                dailySummary.getDinner().stream().allMatch(this::isFoodMeasureAndServingValid) &&
                dailySummary.getOther().stream().allMatch(this::isFoodMeasureAndServingValid);
    }

    private boolean isFoodMeasureAndServingValid(PortionIds portionIds) {
        val foodCsv = foodRepository.findByFoodId(portionIds.getFoodId());
        val conversionFactorCsv = conversionFactorRepository.findByFoodIdAndMeasureId(
                portionIds.getFoodId(),
                portionIds.getMeasureId());
        val serving = portionIds.getServing();
        return foodCsv!=null && conversionFactorCsv!=null && serving > 0;
    }
}