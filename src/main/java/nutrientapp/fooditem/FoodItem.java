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
    int foodGroupId;

    private double totalCalculatedCalories() {
        return this.protein*4 + this.carbohydrates*4 + this.fats*9;
    }

    public double getCaloryPercentFromFat() {
        if(totalCalculatedCalories()<1) {
            return 0;
        }
        return this.fats*9/totalCalculatedCalories();
    }

    public double getCaloryPercentFromProtein() {
        if(totalCalculatedCalories()<1) {
            return 0;
        }
        return this.protein*4/totalCalculatedCalories();
    }

    public double getCaloryPercentFromCarbohydrates() {
        if(totalCalculatedCalories()<1) {
            return 0;
        }
        return this.carbohydrates*4/totalCalculatedCalories();
    }
}