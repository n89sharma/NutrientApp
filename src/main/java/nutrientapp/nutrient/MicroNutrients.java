package nutrientapp.nutrient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MicroNutrients {
    private Minerals minerals;
    private Vitamins vitamins;
    private EssentialFats essentialFats;

    @Data
    public static class Minerals {
        private Nutrient sodium;
        private Nutrient iron;
        private Nutrient calcium;
        private Nutrient magnesium;
        private Nutrient potassium;
        private Nutrient zinc;
    }

    @Data
    public static class Vitamins {
        private Nutrient vitaminA;
        private Nutrient vitaminB6;
        private Nutrient vitaminB12;
        private Nutrient vitaminC;
        private Nutrient vitaminD;
    }

    @Data
    public static class EssentialFats {
        private Nutrient omega3;
        private Nutrient omega6;
    }
}
