package nutrientapp.domain.internal;

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
    public void multiplyByFactor(double factor) {
        this.minerals.multiplyByFactor(factor);
        this.vitamins.multiplyByFactor(factor);
        this.essentialFats.multiplyByFactor(factor);
    }

    @JsonIgnore
    public void add(MicroNutrients other) {
        this.minerals.add(other.getMinerals());
        this.vitamins.add(other.getVitamins());
        this.essentialFats.add(other.getEssentialFats());
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
        public void multiplyByFactor(double factor) {
            this.sodium.multiplyByFactor(factor);
            this.iron.multiplyByFactor(factor);
            this.calcium.multiplyByFactor(factor);
            this.magnesium.multiplyByFactor(factor);
            this.potassium.multiplyByFactor(factor);
            this.zinc.multiplyByFactor(factor);
        }

        @JsonIgnore
        public void add(Minerals other) {
            this.sodium.add(other.getSodium());
            this.iron.add(other.getIron());
            this.calcium.add(other.getCalcium());
            this.magnesium.add(other.getMagnesium());
            this.potassium.add(other.getPotassium());
            this.zinc.add(other.getZinc());
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
        public void multiplyByFactor(double factor) {
            this.vitaminA.multiplyByFactor(factor);
            this.vitaminB6.multiplyByFactor(factor);
            this.vitaminB12.multiplyByFactor(factor);
            this.vitaminC.multiplyByFactor(factor);
            this.vitaminD.multiplyByFactor(factor);
        }

        @JsonIgnore
        public void add(Vitamins other) {
            this.vitaminA.add(other.getVitaminA());
            this.vitaminB6.add(other.getVitaminB6());
            this.vitaminB12.add(other.getVitaminB12());
            this.vitaminC.add(other.getVitaminC());
            this.vitaminD.add(other.getVitaminD());
        }
    }

    @Data
    public static class EssentialFats {
        private Nutrient omega3;
        private Nutrient omega6;

        @JsonIgnore
        public void multiplyByFactor(double factor) {
            this.omega3.multiplyByFactor(factor);
            this.omega6.multiplyByFactor(factor);
        }

        @JsonIgnore
        public void add(EssentialFats other){
            this.omega3.add(other.getOmega3());
            this.omega6.add(other.getOmega6());
        }
    }
}
