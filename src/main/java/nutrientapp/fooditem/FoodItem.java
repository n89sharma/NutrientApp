package nutrientapp.fooditem;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "fooditems")
public class FoodItem {

    @Id
    String id;
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
    boolean customItem;
    String userId;
}