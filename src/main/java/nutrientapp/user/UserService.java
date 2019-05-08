package nutrientapp.user;

import lombok.val;
import nutrientapp.domain.internal.BodyWeight;
import nutrientapp.domain.internal.DailySummary;
import nutrientapp.domain.internal.DailySummaryView;
import nutrientapp.domain.internal.DailyTotals;
import nutrientapp.domain.internal.Food;
import nutrientapp.domain.internal.PortionIds;
import nutrientapp.domain.internal.Recipe;
import nutrientapp.domain.repositories.*;
import nutrientapp.fooditem.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private UserWeightRepository userWeightRepository;
    private ConversionFactorRepository conversionFactorRepository;
    private FoodRepository foodRepository;
    private DailySummaryRepository dailySummaryRepository;
    private RecipeRepository recipeRepository;
    private FoodItemService foodItemService;

    @Autowired
    public UserService(
        UserWeightRepository userWeightRepository,
        ConversionFactorRepository conversionFactorRepository,
        FoodRepository foodRepository,
        DailySummaryRepository dailySummaryRepository,
        RecipeRepository recipeRepository,
        FoodItemService foodItemService) {

        this.userWeightRepository = userWeightRepository;
        this.conversionFactorRepository = conversionFactorRepository;
        this.foodRepository = foodRepository;
        this.dailySummaryRepository = dailySummaryRepository;
        this.recipeRepository = recipeRepository;
        this.foodItemService = foodItemService;
    }

    public List<BodyWeight> saveWeightAtTime(BodyWeight weightAtTime) {
        userWeightRepository.save(weightAtTime);
        return userWeightRepository.findByUserId(weightAtTime.getUserId());
    }

    public DailySummary saveDailySummary(DailySummary dailySummary, Date date) {
        dailySummary.setDate(date); //TODO: Not sure if this is the right way to do it.
        if (isDailySummaryValid(dailySummary)) {
            val dbDailySummary = dailySummaryRepository.findByUserIdAndDate(
                    dailySummary.getUserId(),
                    dailySummary.getDate());
            if (dbDailySummary != null) {
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
        val foodCsv = foodRepository.findOne(portionIds.getFoodId());
        val conversionFactorCsv = conversionFactorRepository.findByFoodIdAndMeasureId(
                portionIds.getFoodId(),
                portionIds.getMeasureId());
        val serving = portionIds.getServing();
        return foodCsv != null && conversionFactorCsv != null && serving > 0;
    }

    public DailySummaryView getDailySummary(String userId, Date date) {
        val dailySummaryView = new DailySummaryView();
        dailySummaryView.setUserId(userId);
        dailySummaryView.setDate(date);

        val dailySummary = dailySummaryRepository.findByUserIdAndDate(userId, date);
        if (dailySummary != null) {
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

    private DailySummaryView.Portion getPortion(String foodId, String measureId, double serving) {
        val food = foodItemService.getFoodItem(foodId, measureId, serving);
        val measure = foodItemService.getMeasure(foodId, measureId);
        return new DailySummaryView.Portion(food, measure, serving);
    }

    public DailyTotals getDailyTotals(String userId, Date date) {
        val dailySummary = getDailySummary(userId, date);
        val dailyPortions = dailySummary.getPortionsInTheDay();
        val dailyTotals = new DailyTotals();

        if (dailyPortions.size() > 0) {
            val totalCalories = getTotalOfValue(dailyPortions, Food::getCalories);

            val totalFats = getTotalOfValue(dailyPortions, f -> f.getMacroNutrients().getFats().getAmountValue());
            val totalCarbohydrates = getTotalOfValue(dailyPortions, f -> f.getMacroNutrients().getCarbohydrates().getAmountValue());
            val totalProteins = getTotalOfValue(dailyPortions, f -> f.getMacroNutrients().getProtein().getAmountValue());

            val totalFatCalorie = totalFats * 9.0;
            val totalCarbohydrateCalorie = totalCarbohydrates * 4.0;
            val totalProteinCalorie = totalProteins * 4.0;
            val totalOtherCalorieRaw = totalCalories - totalFatCalorie - totalCarbohydrateCalorie - totalProteinCalorie;
            val totalOtherCalorie = totalOtherCalorieRaw < 0 ? 0 : totalOtherCalorieRaw;

            val caloriePercentFromFats = totalFatCalorie / totalCalories;
            val caloriePercentFromCarbohydrates = totalCarbohydrateCalorie / totalCalories;
            val caloriePercentFromProteins = totalProteinCalorie / totalCalories;
            val caloriePercentFromOtherMacro = totalOtherCalorie / totalCalories;


            dailyTotals.setMacroDistribution(
                    caloriePercentFromProteins,
                    caloriePercentFromCarbohydrates,
                    caloriePercentFromFats,
                    caloriePercentFromOtherMacro,
                    totalProteins,
                    totalCarbohydrates,
                    totalFats,
                    totalCalories);

            val totalBreakfast = getTotalOfValue(dailySummary.getBreakfast(), Food::getCalories);
            val totalLunch = getTotalOfValue(dailySummary.getLunch(), Food::getCalories);
            val totalDinner = getTotalOfValue(dailySummary.getDinner(), Food::getCalories);
            val totalOther = getTotalOfValue(dailySummary.getOther(), Food::getCalories);

            val caloriePercentFromBreakfast = totalBreakfast / totalCalories;
            val caloriePercentFromLunch = totalLunch / totalCalories;
            val caloriePercentFromDinner = totalDinner / totalCalories;
            val caloriePercentFromOtherMeal = totalOther / totalCalories;

            dailyTotals.setMealDistribution(
                    caloriePercentFromBreakfast,
                    caloriePercentFromLunch,
                    caloriePercentFromDinner,
                    caloriePercentFromOtherMeal,
                    totalBreakfast,
                    totalLunch,
                    totalDinner,
                    totalOther);
        }


        return dailyTotals;
    }

    private double getTotalOfValue(List<DailySummaryView.Portion> portions, Function<Food, Double> getValue) {
        return portions
                .stream()
                .map(DailySummaryView.Portion::getFood)
                .map(getValue)
                .reduce(0.0, (a, b) -> a + b);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}