package fooditem;

import java.util.Map;
import lombok.Data;

@Data
public class ConversionFactorFoodItem {
    int foodId;
    Map<String, Double> conversionFactors;

    public static ConversionFactorFoodItem of(int foodId, Map<String, Double> conversionFactors) {
        ConversionFactorFoodItem conversionFactor = new ConversionFactorFoodItem();
        conversionFactor.foodId = foodId;
        conversionFactor.conversionFactors = conversionFactors;
        return conversionFactor;
    }
}