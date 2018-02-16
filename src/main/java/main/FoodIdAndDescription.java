package main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FoodIdAndDescription {
    int foodId;
    String foodDescription;

    public static FoodIdAndDescription of(int foodId, String foodDescription) {
        return new FoodIdAndDescription(foodId, foodDescription);
    }
}
