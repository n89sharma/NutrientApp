package nutrientapp.utils;

import nutrientapp.domain.csvobjects.FoodCsv;
import nutrientapp.domain.csvobjects.NutrientAmountCsv;
import nutrientapp.domain.repositories.FoodGroupRepository;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.MeasureNameRepository;
import nutrientapp.fooditem.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class DataInput {

    private FoodRepository foodRepository;
    private MeasureNameRepository measureNameRepository;
    private FoodGroupRepository foodGroupRepository;

    @Autowired
    public DataInput(
        FoodRepository foodRepository,
        MeasureNameRepository measureNameRepository,
        FoodGroupRepository foodGroupRepository) {

        this.foodRepository = foodRepository;
        this.measureNameRepository = measureNameRepository;
        this.foodGroupRepository = foodGroupRepository;
    }

    public static Food foodItemOf(FoodCsv foodCsv, List<NutrientAmountCsv> nutrientAmounts) {
        Food food = new Food();
        food.setFoodId(foodCsv.getFoodId());
        food.setFoodDescription(foodCsv.getFoodDescription());

        //@formatter:off
        food.setCalories(       getNutrientAmount(208, nutrientAmounts));
        food.setProtein(        getNutrientAmount(203, nutrientAmounts));
        food.setCarbohydrates(  getNutrientAmount(205, nutrientAmounts));
        food.setSugars(         getNutrientAmount(269, nutrientAmounts));
        food.setFibre(          getNutrientAmount(291, nutrientAmounts));
        food.setFats(           getNutrientAmount(204, nutrientAmounts));
        food.setSaturatedFats(  getNutrientAmount(606, nutrientAmounts));
        food.setTransFats(      getNutrientAmount(605, nutrientAmounts));
        food.setCholesterol(    getNutrientAmount(601, nutrientAmounts));
        food.setSodium(         getNutrientAmount(307, nutrientAmounts));
        food.setIron(           getNutrientAmount(303, nutrientAmounts));
        food.setCalcium(        getNutrientAmount(301, nutrientAmounts));

        double retinolAmount =      getNutrientAmount(319, nutrientAmounts);
        double betaCaroteneAMount = getNutrientAmount(321, nutrientAmounts);

        food.setVitaminA(       retinolAmount*3.33 + betaCaroteneAMount*1.66);
        food.setVitaminB12(     getNutrientAmount(418, nutrientAmounts));
        food.setVitaminC(       getNutrientAmount(401, nutrientAmounts));
        food.setVitaminD(       getNutrientAmount(324, nutrientAmounts));
        food.setOmega3(         getNutrientAmount(868, nutrientAmounts));
        food.setOmega6(         getNutrientAmount(869, nutrientAmounts));
        //@formatter:on


        food.setFoodGroupId(foodCsv.getFoodGroupId());

        return food;
    }

    private static double getNutrientAmount(int nutrientId, List<NutrientAmountCsv> nutrientAmounts) {
        return nutrientAmounts
            .stream()
            .filter(nutrientAmount -> nutrientAmount.getNutrientId() == nutrientId)
            .findFirst()
            .map(NutrientAmountCsv::getNutrientValue)
            .orElse(0.0);
    }
}