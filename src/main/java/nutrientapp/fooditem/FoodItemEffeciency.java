package nutrientapp.fooditem;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodItemEffeciency {
    private int foodId;
    private String foodDescription;
    private double effeciency;

    public static FoodItemEffeciency of(int foodId, String foodDescription, double effeciency) {
        return new FoodItemEffeciency(foodId, foodDescription, effeciency);
    }
}