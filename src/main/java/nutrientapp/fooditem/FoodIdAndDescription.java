package nutrientapp.fooditem;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "foodIdAndDescriptions")
public class FoodIdAndDescription {

    @Id
    String id;
    int foodId;
    String foodDescription;

    public static FoodIdAndDescription of(int foodId, String foodDescription) {
        return new FoodIdAndDescription(null, foodId, foodDescription);
    }
}
