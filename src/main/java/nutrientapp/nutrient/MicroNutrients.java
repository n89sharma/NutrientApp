package nutrientapp.nutrient;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
public class MicroNutrients {
    private Minerals minerals;
    private Vitamins vitamins;
    private EssentialFats essentialFats;

    @JsonIgnore
    public void multiplyByFactor(double factor){
        this.minerals.multiplyByFactor(factor);
        this.vitamins.multiplyByFactor(factor);
        this.essentialFats.multiplyByFactor(factor);
    }

    @Data
    public static class Minerals {
        private Nutrient sodium;
        private Nutrient iron;
        private Nutrient calcium;
        private Nutrient magnesium;
        private Nutrient potassium;
        private Nutrient zinc;

        @JsonIgnore
        public void multiplyByFactor(double factor){
            this.sodium.multiplyByFactor(factor);
            this.iron.multiplyByFactor(factor);
            this.calcium.multiplyByFactor(factor);
            this.magnesium.multiplyByFactor(factor);
            this.potassium.multiplyByFactor(factor);
            this.zinc.multiplyByFactor(factor);
        }
    }

    @Data
    public static class Vitamins {
        private Nutrient vitaminA;
        private Nutrient vitaminB6;
        private Nutrient vitaminB12;
        private Nutrient vitaminC;
        private Nutrient vitaminD;

        @JsonIgnore
        public void multiplyByFactor(double factor){
            this.vitaminA.multiplyByFactor(factor);
            this.vitaminB6.multiplyByFactor(factor);
            this.vitaminB12.multiplyByFactor(factor);
            this.vitaminC.multiplyByFactor(factor);
            this.vitaminD.multiplyByFactor(factor);
        }
    }

    @Data
    public static class EssentialFats {
        private Nutrient omega3;
        private Nutrient omega6;

        @JsonIgnore
        public void multiplyByFactor(double factor){
            this.omega3.multiplyByFactor(factor);
            this.omega6.multiplyByFactor(factor);
        }
    }
}
