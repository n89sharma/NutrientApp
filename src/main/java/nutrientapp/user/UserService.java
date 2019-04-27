package nutrientapp.user;

import java.util.Date;
import java.util.List;

import nutrientapp.fooditem.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.val;
import nutrientapp.domain.repositories.ConversionFactorRepository;
import nutrientapp.domain.repositories.DailySummaryRepository;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.UserWeightRepository;
import nutrientapp.user.DailySummary.PortionIds;
import nutrientapp.user.DailySummary;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private UserWeightRepository userWeightRepository;
    private ConversionFactorRepository conversionFactorRepository;
    private FoodRepository foodRepository;
    private DailySummaryRepository dailySummaryRepository;
    private FoodItemService foodItemService;

    @Autowired
    public UserService(
            UserWeightRepository userWeightRepository,
            ConversionFactorRepository conversionFactorRepository,
            FoodRepository foodRepository,
            DailySummaryRepository dailySummaryRepository,
            FoodItemService foodItemService) {

        this.userWeightRepository = userWeightRepository;
        this.conversionFactorRepository = conversionFactorRepository;
        this.foodRepository = foodRepository;
        this.dailySummaryRepository = dailySummaryRepository;
        this.foodItemService = foodItemService;
    }

    public List<BodyWeight> saveWeightAtTime(BodyWeight weightAtTime) {
        userWeightRepository.save(weightAtTime);
        return userWeightRepository.findByUserId(weightAtTime.getUserId());
    }

    public DailySummary saveDailySummary(DailySummary dailySummary, Date date) {
        dailySummary.setDate(date); //TODO: Not sure if this is the right way to do it.
        if(isDailySummaryValid(dailySummary)) {
            val dbDailySummary = dailySummaryRepository.findByUserIdAndDate(
                dailySummary.getUserId(),
                dailySummary.getDate());
            if(dbDailySummary != null) {
                dailySummary.setId(dbDailySummary.getId());
            }
            return dailySummaryRepository.save(dailySummary);
        }
        return null;
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

    public DailySummaryView getDailySummary(String userId, Date date) {
        val dailySummaryView = new DailySummaryView();
        dailySummaryView.setUserId(userId);
        dailySummaryView.setDate(date);

        val dailySummary = dailySummaryRepository.findByUserIdAndDate(userId, date);
        if(dailySummary != null) {
            val breakfast = getPortions(dailySummary.getBreakfast());
            val lunch = getPortions(dailySummary.getLunch());
            val dinner = getPortions(dailySummary.getDinner());
            val other = getPortions(dailySummary.getOther());

            dailySummaryView.setBreakfast(breakfast);
            dailySummaryView.setLunch(lunch);
            dailySummaryView.setDinner(dinner);
            dailySummaryView.setOther(other);
        }
        return dailySummaryView;
    }

    private List<DailySummaryView.Portion> getPortions(List<PortionIds> portionIds) {
        return portionIds.stream()
                .map(p -> getPortion(p.getFoodId(), p.getMeasureId(), p.getServing()))
                .collect(toList());
    }

    private DailySummaryView.Portion getPortion(int foodId, int measureId, double serving) {
        val food = foodItemService.getFoodItem(foodId, measureId, serving);
        val measure = foodItemService.getMeasure(foodId, measureId);
        return new DailySummaryView.Portion(food, measure, serving);
    }

}