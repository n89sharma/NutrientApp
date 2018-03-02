package fooditem;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "foodIdAndDescriptions")
public class FoodIdAndDescription {
    int foodId;
    String foodDescription;

    public static FoodIdAndDescription of(int foodId, String foodDescription) {
        return new FoodIdAndDescription(foodId, foodDescription);
    }
}
