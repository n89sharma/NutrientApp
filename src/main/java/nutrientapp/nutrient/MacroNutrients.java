package nutrientapp.nutrient;

import lombok.Data;

@Data
public class MacroNutrients {
    private Nutrient protein;
    private Nutrient carbohydrates;
    private Nutrient sugars;
    private Nutrient fibre;
    private Nutrient fats;
    private Nutrient saturatedFats;
    private Nutrient transFats;
    private Nutrient cholesterol;
}
