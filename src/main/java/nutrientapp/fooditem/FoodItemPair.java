package nutrientapp.fooditem;

import lombok.Data;

@Data
public class FoodItemPair {
    private FoodItem oldFoodItem;
    private FoodItem newFoodItem;
}
