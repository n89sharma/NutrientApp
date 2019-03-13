package nutrientapp.fooditem;

import lombok.Data;

@Data
public class Food {

    private int foodId;
    private String foodDescription;
    private double calories;

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

    @Data
    public class MicroNutrients {

        @Data
        public class Minerals {
            private Nutrient sodium;
            private Nutrient iron;
            private Nutrient calcium;
            private Nutrient magnesium;
            private Nutrient potassium;
            private Nutrient zinc;
        }

        @Data
        public class Vitamins {
            private Nutrient vitaminA;
            private Nutrient vitaminB6;
            private Nutrient vitaminB12;
            private Nutrient vitaminC;
            private Nutrient vitaminD;
        }

        @Data
        public class EssentialFats {
            private Nutrient omega3;
            private Nutrient omega6;
        }
    }
}