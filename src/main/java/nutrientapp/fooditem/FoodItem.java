package nutrientapp.fooditem;

import nutrientapp.dbobjects.Food;
import nutrientapp.dbobjects.NutrientAmount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "fooditems")
public class FoodItem {

    int foodId;
    String foodDescription;
    double calories;
    double protein;
    double carbohydrates;
    double sugars;
    double fibre;
    double fats;
    double saturatedFats;
    double transFats;
    double cholesterol;
    double sodium;
    double iron;
    double calcium;
    double vitaminA;
    double vitaminB12;
    double vitaminC;
    double vitaminD;
    double omega3;
    double omega6;

    public static FoodItem of(Food food, List<NutrientAmount> nutrientAmounts) {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodId(food.getFoodId());
        foodItem.setFoodDescription(food.getFoodDescription());

        //@formatter:off
        foodItem.setCalories(       getNutrientAmount(208, nutrientAmounts));
        foodItem.setProtein(        getNutrientAmount(203, nutrientAmounts));
        foodItem.setCarbohydrates(  getNutrientAmount(205, nutrientAmounts));
        foodItem.setSugars(         getNutrientAmount(269, nutrientAmounts));
        foodItem.setFibre(          getNutrientAmount(291, nutrientAmounts));
        foodItem.setFats(           getNutrientAmount(204, nutrientAmounts));
        foodItem.setSaturatedFats(  getNutrientAmount(606, nutrientAmounts));
        foodItem.setTransFats(      getNutrientAmount(605, nutrientAmounts));
        foodItem.setCholesterol(    getNutrientAmount(601, nutrientAmounts));
        foodItem.setSodium(         getNutrientAmount(307, nutrientAmounts));
        foodItem.setIron(           getNutrientAmount(303, nutrientAmounts));
        foodItem.setCalcium(        getNutrientAmount(301, nutrientAmounts));

        double retinolAmount =      getNutrientAmount(319, nutrientAmounts);
        double betaCaroteneAMount = getNutrientAmount(321, nutrientAmounts);

        foodItem.setVitaminA(       retinolAmount*3.33 + betaCaroteneAMount*1.66);
        foodItem.setVitaminB12(     getNutrientAmount(418, nutrientAmounts));
        foodItem.setVitaminC(       getNutrientAmount(401, nutrientAmounts));
        foodItem.setVitaminD(       getNutrientAmount(324, nutrientAmounts));
        foodItem.setOmega3(         getNutrientAmount(868, nutrientAmounts));
        foodItem.setOmega6(         getNutrientAmount(869, nutrientAmounts));
        //@formatter:on

        return foodItem;
    }

    private static double getNutrientAmount(int nutrientId, List<NutrientAmount> nutrientAmounts) {
        return nutrientAmounts
            .stream()
            .filter(nutrientAmount -> nutrientAmount.getNutrientId() == nutrientId)
            .findFirst()
            .map(NutrientAmount::getNutrientValue)
            .orElse(0.0);
    }
}